/**
 * Created by wunan on 15-5-15.
 */
package com.etong.pt.provider.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.dc.auth.AuthService;
import com.etong.dc.auth.TokenParam;
import com.etong.pt.api.session.PtSessionService;
import com.etong.pt.dao.PtUserDao;
import com.etong.pt.data.session.PtSession;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.provider.user.constants.Enumconstant;
import com.etong.pt.provider.user.constants.Validator;
import com.etong.pt.utility.Md5Helper;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.sms.client.SmsClient;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class VerifyManager {
    public static final int MAX_RESEND_TIME = 60;               //1min
    public static final int MAX_VERIFY_TIME = 60 * 15;           //15min
    public static final int MAX_LOGIN_TIME = 60 * 3;            //3min
    public static final int MAX_REFRESH_TOKEN_TIME = 60 * 30;   //30min
    public static final String SMS_SEND_ID = "10006";
    private static VerifyManager instance = new VerifyManager();
    private SmsClient smsClient;
    private PtUserDao ptUserDao;
    private PtSessionService ptSessionService;
    private AuthService authService;
    private Random random = new SecureRandom();
    private static Logger logger = LoggerFactory.getLogger(VerifyManager.class);

    private VerifyManager() {
    }

    public static VerifyManager getInstance() {
        return instance;
    }

    public void setServerUrl(String url) {
        smsClient = new SmsClient(url);
    }

    public void setPtUserDao(PtUserDao ptUserDao) {
        this.ptUserDao = ptUserDao;
    }

    public void setPtSessionService(PtSessionService ptSessionService) {
        this.ptSessionService = ptSessionService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public PtResult sendCode(String phone) {
        if (phone == null || phone.isEmpty()) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER, "手机号码为空", null);
        }

        String verifyCode = this.generateVerify();
        //获取当前时间的10位时间戳
        int now = new Long(new Date().getTime()/1000).intValue();

        PtSession ptSession = null;
        PtResult ptResult = ptSessionService.getOneByParam(phone, Enumconstant.ClientType.SendMsg.getValue());
        if(ptResult.isSucceed()) {
            ptSession = ptResult.getObject();

            if(ptSession.getF_verifytime() != null && (now - ptSession.getF_verifytime() < MAX_RESEND_TIME)) {
                logger.warn("重复发送短信:" + phone);
                return new PtResult(PtCommonError.PT_ERROR_SMS_REDUPLICATED, null, null);
            }
            else {
                if(this.sendVerify(phone, verifyCode)) {
                    ptSession.setF_verifycode(verifyCode);
                    ptSession.setF_verifytime(now);
                    ptResult = ptSessionService.update(ptSession);
                    if(!ptResult.isSucceed()) {
                        return ptResult;
                    }
                }
                else {
                    return new PtResult(PtCommonError.PT_ERROR_SMS_SEND, null, null);
                }
            }
        }
        else {
            if(this.sendVerify(phone, verifyCode)) {
                ptSession = new PtSession();
                ptSession.setF_phone(phone);
                ptSession.setF_verifycode(verifyCode);
                ptSession.setF_verifytime(now);
                ptSessionService.add(ptSession);
                if(!ptResult.isSucceed()) {
                    return ptResult;
                }
            }
            else {
                return new PtResult(PtCommonError.PT_ERROR_SMS_SEND, null, null);
            }
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }

    public PtResult verifyCode(String phone, String code) {
        //获取当前时间的10位时间戳
        int now = new Long(new Date().getTime()/1000).intValue();

        PtSession ptSession = null;
        PtResult ptResult = ptSessionService.getOneByParam(phone, Enumconstant.ClientType.SendMsg.getValue());
        if(ptResult.isSucceed()) {
            ptSession = ptResult.getObject();

            if(now - ptSession.getF_verifytime() > MAX_VERIFY_TIME) {
                logger.warn("验证超时，phone: {}, code: {}", phone, code);
                ptSessionService.delete(ptSession.getF_msid());
                return new PtResult(PtCommonError.PT_ERROR_VERIFY_OVERTIME, null, null);
            }
            else {
                if(!ptSession.getF_verifycode().equalsIgnoreCase(code)) {
                    logger.warn("验证码错误，phone: {}, code: {}", phone, code);
                    return new PtResult(PtCommonError.PT_ERROR_VERIFY_WRONG, null, null);
                }
            }
        }
        else {
            logger.warn("验证异常，phone: {}, code: {}", phone, code);
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_OVERTIME, null, null);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }

    public PtResult getLoginKey(String loginName, String appId) {
        PtUser param = new PtUser();
        if(Validator.isMobile(loginName)) {
            param.setF_phone(loginName);
        }
        else if(Validator.isEmail(loginName)) {
            param.setF_email(loginName);
        }
        else {
            param.setF_name(loginName);
        }
        PtResult ptResult = ptUserDao.getOneByParam(param);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        PtUser ptUser = ptResult.getObject();
        if (ptUser.getF_verify()) {
            ptResult = this.sendCode(ptUser.getF_phone());
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
        }

        //获取当前时间的10位时间戳
        int now = new Long(new Date().getTime()/1000).intValue();

        PtSession ptSession = null;
        ptResult = ptSessionService.getOneByParam(ptUser.getF_mid(), Enumconstant.ClientType.Login.getValue(), appId);
        if(ptResult.isSucceed()) {
            ptSession = ptResult.getObject();
            ptSession.setF_createtime(now);
            ptSession.setF_clienttype(Enumconstant.ClientType.Login.getValue());
            ptResult = ptSessionService.update(ptSession);
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
        }
        else {
            ptSession = new PtSession();
            ptSession.setF_mid(ptUser.getF_mid());
            ptSession.setF_createtime(now);
            ptSession.setF_stid(appId);
            ptSession.setF_phone(loginName);
            ptSession.setF_clienttype(Enumconstant.ClientType.Login.getValue());
            ptResult = ptSessionService.add(ptSession);
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
        }

        LoginKey loginKey = new LoginKey();
        loginKey.setVerify(ptUser.getF_verify());
        loginKey.setSalt(ptUser.getF_salt());
        loginKey.setExtraKey(Integer.toString(now));
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, loginKey);
    }

    public PtResult doLogin(String loginName, String pwd, String verify, String appId) {
        PtUser param = new PtUser();
        if(Validator.isMobile(loginName)) {
            param.setF_phone(loginName);
        }
        else if(Validator.isEmail(loginName)) {
            param.setF_email(loginName);
        }
        else {
            param.setF_name(loginName);
        }
        PtResult ptResult = ptUserDao.getOneByParam(param);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        PtUser ptUser = ptResult.getObject();
        if (ptUser.getF_verify()) {
            ptResult = this.verifyCode(ptUser.getF_phone(), verify);
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
        }

        //获取当前时间的10位时间戳
        int now = new Long(new Date().getTime()/1000).intValue();

        PtSession ptSession = null;
        ptResult = ptSessionService.getOneByParam(ptUser.getF_mid(), Enumconstant.ClientType.Login.getValue(), appId);
        if(ptResult.isSucceed()) {
            ptSession = ptResult.getObject();
        }
        else {
            logger.warn("登录会话不存在，loginName: {}, appId: {}", loginName, appId);
            return new PtResult(PtCommonError.PT_ERROR_LOGON_OVERTIME, null, null);
        }

        if(now - ptSession.getF_createtime() > MAX_LOGIN_TIME) {
            logger.warn("登录会话超时，loginName: {}, appId: {}", loginName, appId);
            ptSessionService.delete(ptSession.getF_msid());
            return new PtResult(PtCommonError.PT_ERROR_LOGON_OVERTIME, null, null);
        }

        String tmpPwd = Md5Helper.Str2MD5(ptUser.getF_password()
                + String.valueOf(ptSession.getF_createtime()));

        //二次哈希加密
        //请求方发过来的密码为MD5(MD5(密码+盐值) + 附加key)
        if (!tmpPwd.equals(pwd)) {
            logger.warn("密码验证失败，loginName: {}, appId: {}", loginName, appId);
            return new PtResult(PtCommonError.PT_ERROR_PASSWORD, "密码错误", null);
        }

        TokenParam tokenParam = new TokenParam();
        tokenParam.setUserId(ptUser.getF_mid().longValue());
        tokenParam.setSystem(appId);
        tokenParam.setIssuer("admin_" + appId);
        tokenParam.setExpireTime(600);
        ptResult = authService.createToken(tokenParam);

        if (!ptResult.isSucceed()) {
            logger.error("Token生成错误，loginName: {}, appId: {}, result: {}"
                    , loginName, appId, ptResult.toString());
            return ptResult;
        }

        AuthorizationInfo authorizationInfo = new AuthorizationInfo();
        authorizationInfo.setToken((String) ptResult.getObject());

//        try {
//            authorizationInfo.setToken(generateToken(ptUser, appId));
//        } catch (JoseException e) {
//            logger.error("Token生成错误，loginName: {}, appId: {}", loginName, appId);
//            return new PtResult(PtCommonError.PT_ERROR_GEN_TOKEN, e.getMessage(), null);
//        }
        authorizationInfo.setAppId(appId);
        authorizationInfo.setUserId(ptUser.getF_mid());
        authorizationInfo.setUserName(ptUser.getF_name());
        authorizationInfo.setPhone(ptUser.getF_phone());
        authorizationInfo.setEmail(ptUser.getF_email());

        logger.info("登录成功，userId: {}, token: {}", ptUser.getF_mid(), authorizationInfo.getToken());
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, authorizationInfo);
    }

    public PtResult doLogin(String phone, String verify, String appId) {
        return null;
    }

    public PtResult resetPassword(String phone, String pwd, String verify) {
        PtResult ptResult = verifyCode(phone, verify);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        ptResult = ptUserDao.setPwdByPhone(phone, pwd, verify);
        return ptResult;
    }

    public PtResult modifyPassword(String token, String oldPwd, String newPwd) {
        PtResult ptResult = verifyToken(token);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        try {
            AuthorizationInfo authorizationInfo = ptResult.getObject();
            int userId = authorizationInfo.getUserId();
            ptResult = ptUserDao.modifyPassword(userId, oldPwd, newPwd);
            return ptResult;
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_RPC, e.getMessage(), null);
        }
    }

    public PtResult verifyToken(String jwt) {
        // Build a JwtConsumer that doesn't check signatures or do any validation.
        JwtConsumer firstPassJwtConsumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();

        try {
            JwtContext jwtContext = firstPassJwtConsumer.process(jwt);

            // From the JwtContext we can get the issuer, or whatever else we might need,
            // to lookup or figure out the kind of validation policy to apply
            JwtClaims jwtClaims = jwtContext.getJwtClaims();

            Integer userId = Integer.parseInt(jwtContext.getJoseObjects().get(0).getKeyIdHeaderValue());
            // get ptUser from db
            PtUser ptUser = null;
            PtResult ptResult = ptUserDao.getById(userId);
            if(ptResult.isSucceed()) {
                ptUser = ptResult.getObject();
            }
            String appId = jwtClaims.getSubject().substring("login".length());
            // get jwkJson from db
            String jwkJson = "";
            ptResult = ptSessionService.getOneByParam(ptUser.getF_mid(), Enumconstant.ClientType.Login.getValue(), appId);
            if(ptResult.isSucceed()) {
                PtSession ptSession = ptResult.getObject();
                jwkJson = ptSession.getF_jwkjson();
            }

            //Map params = JsonUtil.parseJson(jwkJson);
            //RsaJsonWebKey rsaJsonWebKey = new RsaJsonWebKey(params);
            JsonWebKey jwk = JsonWebKey.Factory.newJwk(jwkJson);
            if(!verifySign(jwt, jwk)) {
                return new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN, "令牌签名错误", null);
            }

            // Just using the same key here but you might, for example, have a JWKS URIs configured for
            // each issuer, which you'd use to set up a HttpsJwksVerificationKeyResolver
            Key verificationKey = jwk.getKey();

            // Using info from the JwtContext, this JwtConsumer is set up to verify
            // the signature and validate the claims.
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setExpectedIssuer(jwtClaims.getIssuer())
                    .setExpectedAudience(jwtClaims.getAudience().get(0))
                    .setVerificationKey(verificationKey)
                    .setRequireExpirationTime()
                    .setAllowedClockSkewInSeconds(30)
                    .build();

            try {
                // Finally using the second JwtConsumer to actually validate the JWT. This operates on
                // the JwtContext from the first processing pass, which avoids redundant parsing/processing.
                jwtConsumer.processContext(jwtContext);

                AuthorizationInfo authorizationInfo = new AuthorizationInfo();
                authorizationInfo.setToken(jwt);
                authorizationInfo.setAppId(appId);
                authorizationInfo.setUserId(ptUser.getF_mid());
                authorizationInfo.setUserName(ptUser.getF_name());
                authorizationInfo.setPhone(ptUser.getF_phone());
                authorizationInfo.setEmail(ptUser.getF_email());

                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, authorizationInfo);
            } catch (InvalidJwtException e) {
                if(verifyExp(jwtClaims, MAX_REFRESH_TOKEN_TIME)) {
                    //generateToken
                    String refreshToken = generateToken(ptUser, appId);
                    AuthorizationInfo authorizationInfo = new AuthorizationInfo();
                    authorizationInfo.setToken(refreshToken);
                    authorizationInfo.setAppId(appId);
                    authorizationInfo.setUserId(ptUser.getF_mid());
                    authorizationInfo.setUserName(ptUser.getF_name());
                    authorizationInfo.setPhone(ptUser.getF_phone());
                    authorizationInfo.setEmail(ptUser.getF_email());
                    return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, authorizationInfo);
                } else {
                    logger.error("Token续约失败，Exception:" + e.getMessage());
                    return new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN, e.getMessage(), null);
                }

            }

        } catch (Exception e) {
            logger.error("Token校检失败，Exception:" + e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN, e.getMessage(), null);
        }
    }

    public PtResult destroyToken(String token) {
        PtResult ptResult = verifyToken(token);
        if (ptResult.isSucceed()) {
            AuthorizationInfo authorizationInfo = ptResult.getObject();
            String appId = authorizationInfo.getAppId();
            Integer userId = authorizationInfo.getUserId();
            logger.info("注销成功，userId: {}, token: ｛｝", userId, authorizationInfo.getToken());
            ptResult = ptSessionService.getOneByParam(userId, Enumconstant.ClientType.Login.getValue(), appId);
            if(ptResult.isSucceed()) {
                PtSession ptSession = ptResult.getObject();
                return ptSessionService.delete(ptSession.getF_msid());
            }
        }

        return ptResult;
    }

    private String generateToken(PtUser ptUser, String appId) throws JoseException {
        //获取当前时间的10位时间戳
        int now = new Long(new Date().getTime()/1000).intValue();

        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

        // Give the JWK a Key ID (kid), which is just the polite thing to do
        rsaJsonWebKey.setKeyId(ptUser.getF_mid().toString());
        String jwkJson = rsaJsonWebKey.toJson();

        PtSession ptSession = null;
        PtResult ptResult = ptSessionService.getOneByParam(ptUser.getF_mid(), Enumconstant.ClientType.Login.getValue(), appId);
        if(ptResult.isSucceed()) {
            ptSession = ptResult.getObject();
            ptSession.setF_jwkjson(jwkJson);
            ptResult = ptSessionService.update(ptSession);
            if(!ptResult.isSucceed()) {
                return null;
            }
        }
        else {
            ptSession = new PtSession();
            ptSession.setF_mid(ptUser.getF_mid());
            ptSession.setF_jwkjson(jwkJson);
            ptSession.setF_createtime(now);
            ptSession.setF_clienttype(Enumconstant.ClientType.Login.getValue());
            ptSession.setF_stid(appId);
            ptResult = ptSessionService.add(ptSession);
            if(!ptResult.isSucceed()) {
                return null;
            }
        }

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("dc-user");  // who creates the token and signs it
        claims.setAudience(ptUser.getF_phone()); // to whom the token is intended to be sent
        claims.setSubject("login" + appId);
        claims.setExpirationTimeMinutesInTheFuture(100000000); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid

        //claims.setClaim("id", ptUser.getF_mid());
        //claims.setClaim("name", ptUser.getF_name());
        //claims.setClaim("phone", ptUser.getF_phone());
        //claims.setClaim("email", ptUser.getF_email());// additional claims/attributes about the subject can be added

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        String jwt = jws.getCompactSerialization();
        return jwt;
    }

    /**
     * 验证JWT签名
     * @param requestJwt
     * @param jwk
     * @return
     * @throws JoseException
     */
    private boolean verifySign(String requestJwt, JsonWebKey jwk) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setCompactSerialization(requestJwt);
        jws.setKey(jwk.getKey());
        return jws.verifySignature();
    }

    /**
     * 判断JWT是否满足续约条件
     * @param jwtClaims
     * @param times
     * @return
     * @throws MalformedClaimException
     */
    private boolean verifyExp(JwtClaims jwtClaims, long times) throws MalformedClaimException {
        NumericDate exp = jwtClaims.getExpirationTime();
        exp.addSeconds(times);
        NumericDate now = NumericDate.now();
        return now.isBefore(exp);
    }

    /**
     * 生成短信验证码
     * @return
     */
    private String generateVerify() {
        return String.format("%06d", Math.abs(random.nextInt() % 1000000));
    }

    /**
     * 发送短信
     * @param phone
     * @param code
     * @return
     */
    private Boolean sendVerify(String phone, String code) {
        //发送短信
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("content", "尊敬的用户，您的验证码：" + code);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        String result = smsClient.sendMsgPost(jsonArray.toJSONString(), "", SMS_SEND_ID);
        logger.info("短信发送结果，phone: {}, result: {} ",  phone,  result);
        if (!"004".equals(result)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }
}
