/**
 * Created by wunan on 16-1-29.
 */
package com.etong.captcha.dao;

import com.etong.pt.utility.PtResult;

public interface VerifyData {
    PtResult setCaptcha(Captcha captcha);
    PtResult getCaptcha(String sequence);
    PtResult delCaptcha(String sequence);
}
