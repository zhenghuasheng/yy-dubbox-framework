/**
 * Created by wunan on 16-1-29.
 */
package com.etong.captcha.service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.captcha.dao.Captcha;
import com.etong.captcha.dao.VerifyData;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.utility.SmsHelper;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Path("captcha")
@Produces({ContentType.APPLICATION_JSON_UTF_8})

public class VerifyServiceImpl implements VerifyService {
    private static Logger logger = LoggerFactory.getLogger(VerifyServiceImpl.class);
    private Producer captchaProducer = null;
    private VerifyData verifyData = null;
    private SmsHelper smsHelper = new SmsHelper();

    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    public void setVerifyData(VerifyData verifyData) {
        this.verifyData = verifyData;
    }

    @GET
    @Path("phone/{system}/{phone}/{seq}")
    @Override
    public PtResult getCaptchaByPhone(@PathParam("phone") String phone
            , @PathParam("system") String system
            , @PathParam("seq") String sequence) {
        String capText = captchaProducer.createText();
        PtResult ptResult = verifyData.getCaptcha(makeSequence(system, sequence));

        if ((ptResult.getPtError() == PtCommonError.PT_ERROR_NODATA)
                || !ptResult.isSucceed()) {
            Captcha captcha = new Captcha();
            captcha.setCode(capText);
            captcha.setCreateTime(new Date().getTime());
            captcha.setSystem(system);
            captcha.setSequence(makeSequence(system, sequence));
            verifyData.setCaptcha(captcha);
            return smsHelper.sendMessageRequest(phone, capText, "10006", null);
        }

        Captcha captcha = ptResult.getObject();
        long now = new Date().getTime();

        if ((now - captcha.getCreateTime()) < 60000) {
            return new PtResult(PtCommonError.PT_ERROR_SMS_REDUPLICATED
                    , "60秒内不能重复发送验证码", null);
        }

        captcha.setCode(capText);
        captcha.setCreateTime(now);
        verifyData.setCaptcha(captcha);
        return smsHelper.sendMessageRequest(phone, capText, "10006", null);
    }

    @GET
    @Path("account/{system}/{seq}")
    @Override
    public PtResult getCaptchaByAccount(@PathParam("seq") String sequence
            , @PathParam("system") String system) {
        String capText = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(capText);
        RpcContext context = RpcContext.getContext();
        HttpServletRequest req = context.getRequest(HttpServletRequest.class);
        HttpServletResponse resp = context.getResponse(HttpServletResponse.class);
        Captcha captcha = new Captcha();
        captcha.setCode(capText);
        captcha.setCreateTime(new Date().getTime());
        captcha.setSystem(system);
        captcha.setSequence(makeSequence(system, sequence));

        if ((req == null) || (resp == null)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                ImageIO.write(bi, "jpg", out);
            } catch (IOException e) {
                logger.error("图片写入异常：{}", e.getMessage());
                return new PtResult(PtCommonError.PT_ERROR_IO, "图片写入异常", null);
            }

            verifyData.setCaptcha(captcha);
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, out.toByteArray());
        } else {
            resp.setDateHeader("Expires", 0L);
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
            resp.setHeader("Pragma", "no-cache");
            resp.setContentType("image/jpeg");

            try {
                ServletOutputStream out = resp.getOutputStream();
                ImageIO.write(bi, "jpg", out);
            } catch (IOException e) {
                logger.error("图片写入异常：{}", e.getMessage());
                return new PtResult(PtCommonError.PT_ERROR_IO, "图片写入异常", null);
            }

            verifyData.setCaptcha(captcha);
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        }
    }

    @GET
    @Path("verify/{system}/{seq}/{code}")
    @Override
    public PtResult verifyCode(@PathParam("system") String system
            , @PathParam("seq") String sequence
            , @PathParam("code") String code) {
        String captchaKey = makeSequence(system, sequence);
        PtResult ptResult = verifyData.getCaptcha(captchaKey);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Captcha captcha = ptResult.getObject();
        long now = new Date().getTime();

        if ((now - captcha.getCreateTime()) > 300 * 1000) {
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_OVERTIME
                    , "验证码已经超时", null);
        }

        if (code.compareToIgnoreCase(captcha.getCode()) != 0) {
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_WRONG
                    , "验证码错误", null);
        }

        verifyData.delCaptcha(captchaKey);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }

    private String makeSequence(String system, String sequence) {
        return system + ":" + sequence;
    }
}
