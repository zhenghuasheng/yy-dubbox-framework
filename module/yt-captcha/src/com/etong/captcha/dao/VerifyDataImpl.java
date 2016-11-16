/**
 * Created by wunan on 16-1-29.
 */
package com.etong.captcha.dao;

import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.*;

public class VerifyDataImpl implements VerifyData {
    public static final String MEM_CAPTCHA = "MEM:CAPTCHA";

    @UpdateSingleCache(namespace = MEM_CAPTCHA, expiration = 600)
    @ReturnDataUpdateContent
    @Override
    public PtResult setCaptcha(@ParameterValueKeyProvider Captcha captcha) {
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, captcha);
    }

    @ReadThroughSingleCache(namespace = MEM_CAPTCHA, expiration = 600)
    @Override
    public PtResult getCaptcha(@ParameterValueKeyProvider String sequence) {
        return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有找到验证码信息", null);
    }

    @InvalidateSingleCache(namespace = MEM_CAPTCHA)
    @Override
    public PtResult delCaptcha(@ParameterValueKeyProvider String sequence) {
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }
}
