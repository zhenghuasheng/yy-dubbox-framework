/**
 * Created by wunan on 16-2-1.
 */
package com.etong.dc.user.service;

import com.etong.pt.data.service.UserInfo;
import com.etong.pt.utility.PtResult;

public interface UserService {
    PtResult register(UserInfo userInfo);

    PtResult register(UserInfo userInfo, String seq, String captcha);

    PtResult getLoginKey(String account);

    PtResult loginByPwd(String account, String pwd);

    PtResult logout(String account);
}
