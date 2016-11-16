package com.etong.pt.provider.session;

import com.etong.pt.api.session.PtSessionService;
import com.etong.pt.dao.PtSessionDao;
import com.etong.pt.data.session.PtSession;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/10.
 */
public class PtSessionServiceImpl implements PtSessionService {
    private PtSessionDao ptSessionDao;

    public void setPtSessionDao(PtSessionDao ptSessionDao) {
        this.ptSessionDao = ptSessionDao;
    }

    @Override
    public PtResult add(PtSession record) {
        return ptSessionDao.add(record);
    }

    @Override
    public PtResult update(PtSession record) {
        return ptSessionDao.update(record);
    }

    @Override
    public PtResult delete(Long id) {
        return ptSessionDao.delete(id);
    }

    @Override
    public PtResult getById(Long id) {
        return ptSessionDao.getById(id);
    }

    @Override
    public PtResult getOneByParam(PtSession param) {
        return ptSessionDao.getOneByParam(param);
    }

    @Override
    public PtResult getOneByParam(String phone, Integer type) {
        return ptSessionDao.getOneByParam(phone, type);
    }

    @Override
    public PtResult getOneByParam(Integer userId, Integer type, String appId) {
        return ptSessionDao.getOneByParam(userId, type, appId);
    }
}
