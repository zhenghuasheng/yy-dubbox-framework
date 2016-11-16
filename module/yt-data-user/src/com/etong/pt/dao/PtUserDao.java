package com.etong.pt.dao;

import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.user.PtUserExample;
import com.etong.pt.data.user.detail.UserDetail;
import com.etong.pt.data.user.detail.UserDetailExample;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public interface PtUserDao {
    PtResult add(PtUser record);
    PtResult update(PtUser record);
    PtResult delete(Integer id);
    PtResult findAll();
    PtResult getById(Integer id);
    PtResult getByPhone(String phone);
    PtResult getOneByParam(PtUser param);

    /**
     * 密码重置
     * @param phone 手机号
     * @param pwd 新密码
     * @param salt 盐值
     * @return 无
     */
    PtResult setPwdByPhone(String phone, String pwd, String salt);

    /**
     * 更改密码
     * @param id 用户ID
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 无
     */
    PtResult modifyPassword(Integer id, String oldPwd, String newPwd);

    PtResult getUser(PtUserExample example);

    PtResult getUserDetail(UserDetailExample example);

    PtResult putUserDetail(UserDetail userDetail);
}
