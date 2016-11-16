/**
 * Created by wunan on 15-6-8.
 */
package com.etong.pt.data.dc;

import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.db.DictHelper;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;

import java.util.List;

public class DictHelperImpl implements DictHelper {
    private DbManager dbManager;

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult getCategory(int category) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        DictionaryMapper dictionaryMapper = dbProxy.getMapper(DictionaryMapper.class);
        DictionaryExample example = new DictionaryExample();
        example.or().andF_levelEqualTo((byte) 1).andF_ctidEqualTo((short) category);

        try {
            List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);

            if (dictionaryList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, dictionaryList);
            }
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getChildCategory(int category, int parent) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        DictionaryMapper dictionaryMapper = dbProxy.getMapper(DictionaryMapper.class);
        DictionaryExample example = new DictionaryExample();
        example.or().andF_pidEqualTo((short) parent).andF_ctidEqualTo((short) category);

        try {
            List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);

            if (dictionaryList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, dictionaryList);
            }
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getLeaveByValue(int category, int value, int parent){
        DbProxy dbProxy = dbManager.getDbProxy(true);
        DictionaryMapper dictionaryMapper = dbProxy.getMapper(DictionaryMapper.class);
        DictionaryExample example = new DictionaryExample();

        if (parent > 0) {
            example.or().andF_pidEqualTo((short) parent)
                    .andF_ctidEqualTo((short) category)
                    .andF_valueEqualTo(value);
        } else {
            example.or().andF_ctidEqualTo((short) category)
                    .andF_valueEqualTo(value);
        }

        try {
            List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);

            if (dictionaryList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, dictionaryList);
            }
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }
}
