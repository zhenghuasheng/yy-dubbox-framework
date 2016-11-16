/**
 * Created by wunan on 16-1-29.
 */
package com.etong.captcha.service;

import com.etong.pt.utility.PtResult;

public interface VerifyService {
    PtResult getCaptchaByPhone(String phone, String system, String sequence);

    PtResult getCaptchaByAccount(String system, String sequence);

    PtResult verifyCode(String system, String sequence, String code);
}
