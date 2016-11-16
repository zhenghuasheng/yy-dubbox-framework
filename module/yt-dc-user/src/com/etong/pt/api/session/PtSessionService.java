package com.etong.pt.api.session;

import com.etong.pt.data.session.PtSession;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/10.
 */
public interface PtSessionService {
    PtResult add(PtSession record);
    PtResult update(PtSession record);
    PtResult delete(Long id);
    PtResult getById(Long id);
    PtResult getOneByParam(PtSession param);
    PtResult getOneByParam(String phone, Integer type);
    PtResult getOneByParam(Integer userId, Integer type, String appId);
}
