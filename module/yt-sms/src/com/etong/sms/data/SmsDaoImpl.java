package com.etong.sms.data;

import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.sms.model.Message;
import com.etong.sms.utility.SmsError;
import org.apache.log4j.Logger;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/2.
 */

public class SmsDaoImpl implements SmsDao {

    private static Logger logger = Logger.getLogger(SmsDaoImpl.class);
    private DbManager dbManager;

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }


    @Override
    public PtResult addMessageRequest(Message message) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            MessageMapper mapper = dbProxy.getMapper(MessageMapper.class);
            int result = mapper.addMessageRequest(message);
            if (result < 1) {
                return new PtResult(SmsError.SMS_ERROR_SENDFAIL, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, message.getId());
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
    public PtResult getMessageList(Map<String, Object> map) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            MessageMapper mapper = dbProxy.getMapper(MessageMapper.class);
            List<Message> messageList = mapper.getMessageList(map);
            if (messageList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, messageList);
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
    public PtResult deleteMessage(Long id) {
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);
            MessageMapper mapper = dbProxy.getMapper(MessageMapper.class);
            int result = mapper.deleteMessage(id);
            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }
}
