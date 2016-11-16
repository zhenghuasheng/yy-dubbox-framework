package com.etong.pt.dao.impl;

import com.etong.pt.dao.PtVehicleDao;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.data.vehicle.PtVehicleExample;
import com.etong.pt.data.vehicle.PtVehicleMapper;
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
public class PtVehicleDaoImpl implements PtVehicleDao {
    public static final String DC_VEHICLE_INDEX = "dc_vehicle";
    private static final Logger logger = LoggerFactory.getLogger(PtVehicleDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult add(PtVehicle record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(DC_VEHICLE_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_dvid(id);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

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
    public PtResult update(PtVehicle record) {
        PtVehicleExample example = new PtVehicleExample();
        example.or().andF_dvidEqualTo(record.getF_dvid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

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

    public PtResult delete(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

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

    @Override
    public PtResult getListByCustId(Long custId) {
        PtVehicleExample example = new PtVehicleExample();
        example.or().andF_ciidEqualTo(custId);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

        try {
            List<PtVehicle> list = mapper.selectByExample(example);

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

    public PtResult deleteByCustId(Long custId) {
        PtVehicleExample example = new PtVehicleExample();
        example.or().andF_ciidEqualTo(custId);
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper ptVehicleMapper = dbProxy.getMapper(PtVehicleMapper.class);

        try {
            int result = ptVehicleMapper.deleteByExample(example);

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
    public PtResult getVehicle(PtVehicleExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

        try {
            List<PtVehicle> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有查询到客户车辆信息", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error("获取客户车辆异常: {}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取客户车辆异常", null);
        } finally {
            dbProxy.close();
        }
    }
}
