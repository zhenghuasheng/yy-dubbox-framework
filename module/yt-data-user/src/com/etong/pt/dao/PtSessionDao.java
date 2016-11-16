package com.etong.pt.dao;

import com.etong.pt.data.session.PtSession;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public interface PtSessionDao {
    PtResult add(PtSession record);
    PtResult update(PtSession record);
    PtResult delete(Long id);
    PtResult getById(Long id);
    PtResult getOneByParam(PtSession param);
    PtResult getOneByParam(String phone, Integer type);
    PtResult getOneByParam(Integer userId, Integer type, String appId);

}
