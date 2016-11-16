package com.etong.pt.dao.impl;

import com.etong.pt.dao.PtSessionDao;
import com.etong.pt.data.session.PtSession;
import com.etong.pt.data.session.PtSessionExample;
import com.etong.pt.data.session.PtSessionMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public class PtSessionDaoImpl implements PtSessionDao {
    public static final String DC_SESSION_INDEX = "dc_session";
    private static Logger logger = Logger.getLogger(PtSessionDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult add(PtSession record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(DC_SESSION_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_msid(id);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_msid());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
    }

    //    @UpdateSingleCache(namespace = "pt:session", expiration = 3600)
    @Override
    public PtResult update(@ParameterValueKeyProvider @ParameterDataUpdateContent PtSession record) {
        PtSessionExample example = new PtSessionExample();
        example.or().andF_msidEqualTo(record.getF_msid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {
            int result = mapper.updateByExampleSelective(record, example);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    //    @InvalidateSingleCache(namespace = "pt:session")
    @Override
    public PtResult delete(@ParameterValueKeyProvider Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {
            int result = mapper.deleteByPrimaryKey(id);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            }{
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    //    @ReadThroughSingleCache(namespace = "pt:session", expiration = 3600)
    @Override
    public PtResult getById(@ParameterValueKeyProvider Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {
            PtSession record = mapper.selectByPrimaryKey(id);

            if (record == null) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getOneByParam(PtSession param) {
        PtSessionExample example = new PtSessionExample();
        PtSessionExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {
            if(param.getF_mid() != null) {
                criteria.andF_midEqualTo(param.getF_mid());
            }
            if(param.getF_phone() != null && !param.getF_phone().isEmpty()) {
                criteria.andF_phoneEqualTo(param.getF_phone());
            }
            if(param.getF_clienttype() != null) {
                criteria.andF_clienttypeEqualTo(param.getF_clienttype());
            }
            if(param.getF_stid() != null && !param.getF_stid().isEmpty()) {
                criteria.andF_stidEqualTo(param.getF_stid());
            }

            List<PtSession> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list.get(0));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    //    @ReadThroughSingleCache(namespace = "pt:session", expiration = 3600)
    @Override
    public PtResult getOneByParam(@ParameterValueKeyProvider(order = 0)String phone, @ParameterValueKeyProvider(order = 1)Integer type) {
        PtSessionExample example = new PtSessionExample();
        PtSessionExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {

            criteria.andF_phoneEqualTo(phone);
            criteria.andF_clienttypeEqualTo(type);

            List<PtSession> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list.get(0));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    //    @ReadThroughSingleCache(namespace = "pt:session", expiration = 3600)
    @Override
    public PtResult getOneByParam(@ParameterValueKeyProvider(order = 0)Integer userId, @ParameterValueKeyProvider(order = 1)Integer type,
                                  @ParameterValueKeyProvider(order = 2)String appId) {
        PtSessionExample example = new PtSessionExample();
        PtSessionExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtSessionMapper mapper = dbProxy.getMapper(PtSessionMapper.class);

        try {

            criteria.andF_midEqualTo(userId);
            criteria.andF_clienttypeEqualTo(type);
            criteria.andF_stidEqualTo(appId);

            List<PtSession> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list.get(0));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }


}
