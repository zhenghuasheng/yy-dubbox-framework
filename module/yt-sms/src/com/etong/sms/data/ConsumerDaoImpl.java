package com.etong.sms.data;

import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.sms.model.Consumer;
import org.apache.cxf.wsdl.TParam;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/13.
 */
public class ConsumerDaoImpl implements ConsumerDao {
    private Logger logger = Logger.getLogger(ConsumerDaoImpl.class);
    private DbManager dbManager;


    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult addConsumerRequset(Consumer consumer) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            ConsumerMapper consumerMapper = dbProxy.getMapper(ConsumerMapper.class);
            int result = consumerMapper.addConsumerRequset(consumer);
            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, consumer.getId());
        } catch (Exception e) {
            logger.error("客户信息添加失败||" + e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult getConsumerRequest(String memberName, String memberId) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            Map<String, Object> param = new HashMap<String, Object>();
            if (memberName != null) {
                param.put("memberName", memberName);
            }
            if (memberId != null) {
                param.put("memberId", memberId);
            }
            ConsumerMapper consumerMapper = dbProxy.getMapper(ConsumerMapper.class);
            Consumer consumer = consumerMapper.getConsumerRequest(param);
            if (consumer == null) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, consumer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult getConsumerBykey(int id) {
        return null;
    }

    @Override
    public PtResult getConsumers(int start, int count) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("start", start);
            param.put("count", count);
            ConsumerMapper consumerMapper = dbProxy.getMapper(ConsumerMapper.class);
            List<Consumer> consumerList = consumerMapper.getConsumers(param);
            if (consumerList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, consumerList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult updateConsumerRequest(Consumer consumer) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            ConsumerMapper consumerMapper = dbProxy.getMapper(ConsumerMapper.class);
            int result = consumerMapper.updateConsumerRequest(consumer);
            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, consumer.getId());
        } catch (Exception e) {
            logger.error("用户信息修改失败||" + e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }
}
