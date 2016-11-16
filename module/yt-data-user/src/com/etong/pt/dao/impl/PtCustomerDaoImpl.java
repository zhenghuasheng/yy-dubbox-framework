package com.etong.pt.dao.impl;

import com.etong.pt.dao.PtCustomerDao;
import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.customer.PtCustomerExample;
import com.etong.pt.data.customer.PtCustomerMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public class PtCustomerDaoImpl implements PtCustomerDao {
    public static final String DC_CUSTINFO_INDEX = "dc_custinfo";
    private static final Logger logger = LoggerFactory.getLogger(PtCustomerDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult add(PtCustomer record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(DC_CUSTINFO_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_ciid(id);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_ciid());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
    }

    @Override
    public PtResult update(PtCustomer record) {
        PtCustomerExample example = new PtCustomerExample();
        example.or().andF_ciidEqualTo(record.getF_ciid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

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

    @Override
    public PtResult getById(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

        try {
            PtCustomer record = mapper.selectByPrimaryKey(id);

            if (record == null) {
                return new PtResult(PtCommonError.PT_ERROR_CUST_NOTEXIST, null, null);
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
    public PtResult getOneByUserId(Integer userId) {
        PtCustomerExample example = new PtCustomerExample();
        example.or().andF_midEqualTo(userId);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

        try {
            List<PtCustomer> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_CUST_NOTEXIST, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list.get(0));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult findByParam(PtCustomer param) {
        PtCustomerExample example = new PtCustomerExample();
        PtCustomerExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

        try {
            if(param.getF_phone() != null) {
                criteria.andF_phoneEqualTo(param.getF_phone());
            }

            List<PtCustomer> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_CUST_NOTEXIST, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getCustomer(PtCustomerExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

        try {
            List<PtCustomer> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有查询客户资料", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error("获取客户资料异常: {}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取客户资料异常", null);
        } finally {
            dbProxy.close();
        }
    }
}
