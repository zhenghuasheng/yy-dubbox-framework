/**
 * Created by wunan on 2015/4/22.
 */
package com.etong.pt.data.dc;

import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;

import java.util.List;

public class PtIndexHelper implements DbIndexNum {
    public static final String DC_INDEX = "dc:index";
    public static final Long BEGIN_INDEX_NUM = 100L;
    private DbManager dbManager;
    private PtIndexCache indexCache;

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public void setIndexCache(PtIndexCache indexCache) {
        this.indexCache = indexCache;
    }

    @Override
    public PtResult generateIndexNum(String type) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            return generateIndexNum(type, dbProxy);
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult generateIndexNum(String type, DbProxy dbProxy) {
        PtResult ptResult = indexCache.getIndex(type);
        Long index;

        if (ptResult.isSucceed()) {
            index = ptResult.getObject();
        } else if (ptResult.getPtError() == PtCommonError.PT_ERROR_NODATA) {
            index = BEGIN_INDEX_NUM;
        } else {
            return ptResult;
        }

        ++index;
        ptResult = indexCache.setIndex(type, index, dbProxy);

        if (!ptResult.isSucceed()) {
            indexCache.clearCache(type);
        }

        return ptResult;
    }
}
