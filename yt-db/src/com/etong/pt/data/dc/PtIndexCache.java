/**
 * Created by wunan on 16-2-3.
 */
package com.etong.pt.data.dc;

import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.*;

import java.util.List;

public class PtIndexCache {
    public static final String MEM_INDEX = "MEM:INDEX";
    private DbManager dbManager;

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @ReadThroughSingleCache(namespace = MEM_INDEX, expiration = 3600)
    PtResult getIndex(@ParameterValueKeyProvider String type) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtIndexExample ptIndexExample = new PtIndexExample();
        ptIndexExample.or().andF_typeEqualTo(type);

        try {
            PtIndexMapper ptIndexMapper = dbProxy.getMapper(PtIndexMapper.class);
            List<PtIndex> ptIndexList = ptIndexMapper.selectByExample(ptIndexExample);

            if (ptIndexList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            Long index = ptIndexList.get(0).getF_index();
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, index);
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, "读取索引值异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @UpdateSingleCache(namespace = MEM_INDEX, expiration = 3600)
    @ReturnDataUpdateContent
    PtResult setIndex(@ParameterValueKeyProvider String type, Long index, DbProxy dbProxy) {
        PtIndex ptIndex = new PtIndex();
        ptIndex.setF_type(type);
        ptIndex.setF_index(index);
        PtIndexExample ptIndexExample = new PtIndexExample();
        ptIndexExample.or().andF_typeEqualTo(type);
        PtIndexMapper ptIndexMapper = dbProxy.getMapper(PtIndexMapper.class);
        int result = ptIndexMapper.updateByExample(ptIndex, ptIndexExample);

        if (result == 0) {
            result = ptIndexMapper.insert(ptIndex);
        }

        if (result == 1) {
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, index);
        } else {
            return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
        }
    }

    @InvalidateSingleCache(namespace = MEM_INDEX)
    void clearCache(@ParameterValueKeyProvider String type) {
    }
}
