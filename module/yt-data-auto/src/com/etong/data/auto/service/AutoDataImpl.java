/**
 * Created by wunan on 15-11-11.
 */
package com.etong.data.auto.service;

import com.etong.data.auto.car.Car;
import com.etong.data.auto.car.CarExample;
import com.etong.data.auto.car.CarMapper;
import com.etong.data.auto.carparam.CarParam;
import com.etong.data.auto.carparam.CarParamExample;
import com.etong.data.auto.carparam.CarParamMapper;
import com.etong.data.auto.carphoto.CarPhoto;
import com.etong.data.auto.carphoto.CarPhotoExample;
import com.etong.data.auto.carphoto.CarPhotoMapper;
import com.etong.data.auto.expand.vehicle.ExpandVehicle;
import com.etong.data.auto.expand.vehicle.ExpandVehicleExample;
import com.etong.data.auto.expand.vehicle.ExpandVehicleMapper;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.data.auto.vehicle.VehicleExample;
import com.etong.data.auto.vehicle.VehicleMapper;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AutoDataImpl implements AutoData {
    public static final String MEM_VEHICLE = "mem:vehicle";
    public static final String MEM_CAR = "mem:car";
    public static final String MEM_CAR_PARAM = "mem:car:param";
    public static final String MEM_CAR_PHOTO = "mem:car:photo";
    public static final String MEM_VEHICLE_EXPAND = "mem:vehicle:expand";
    private DbManager dbManager;
    private String imagePrefix;
    private static Logger logger = LoggerFactory.getLogger(AutoDataImpl.class);

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
    }

    @ReadThroughSingleCache(namespace = MEM_VEHICLE, expiration = 3600 * 24)
    @Override
    public PtResult getVehicleByExample(VehicleExample example
            , @ParameterValueKeyProvider String cacheKey) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            VehicleMapper mapper = dbProxy.getMapper(VehicleMapper.class);
            List<Vehicle> vehicleList = mapper.selectByExample(example);

            if (vehicleList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            for (Vehicle vehicle : vehicleList) {
                String image = vehicle.getImage();

                if (image == null) {
                    continue;
                }

                if (image.contains("http://") || image.contains("HTTP://")) {
                    continue;
                }

                vehicle.setImage(imagePrefix + image);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, vehicleList);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult setVehicle(Vehicle vehicle) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        VehicleExample example = new VehicleExample();
        example.or().andIdEqualTo(vehicle.getId());

        try {
            VehicleMapper vehicleMapper = dbProxy.getMapper(VehicleMapper.class);
            int result = vehicleMapper.updateByExampleSelective(vehicle, example);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "车型数据更新失败", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("车型数据更新异常: {}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "车型数据更新异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_CAR, expiration = 3600 * 24)
    @Override
    public PtResult getCarByExample(CarExample example
            , @ParameterValueKeyProvider String cacheKey) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            CarMapper mapper = dbProxy.getMapper(CarMapper.class);
            List<Car> carList = mapper.selectByExample(example);

            if (carList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            for (Car car : carList) {
                String image = car.getImage();

                if (image == null) {
                    continue;
                }

                if (image.contains("http://") || image.contains("HTTP://")) {
                    continue;
                }

                car.setImage(imagePrefix + image);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, carList);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult setCar(Car car) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        CarExample carExample = new CarExample();
        carExample.or().andVidEqualTo(car.getVid());

        try {
            CarMapper carMapper = dbProxy.getMapper(CarMapper.class);
            int result = carMapper.updateByExampleSelective(car, carExample);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "车款数据更新失败", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("车款数据更新异常: {}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "车款数据更新异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_CAR_PARAM, expiration = 3600 * 24)
    @Override
    public PtResult getCarParamByExample(CarParamExample example
            , @ParameterValueKeyProvider String cacheKey) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            CarParamMapper mapper = dbProxy.getMapper(CarParamMapper.class);
            List<CarParam> carParamList = mapper.selectByExample(example);

            if (carParamList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, carParamList);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_CAR_PHOTO, expiration = 3600 * 24)
    @Override
    public PtResult getCarPhotoByExample(CarPhotoExample example
            , @ParameterValueKeyProvider String cacheKey) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            CarPhotoMapper mapper = dbProxy.getMapper(CarPhotoMapper.class);
            List<CarPhoto> carPhotoList = mapper.selectByExample(example);

            if (carPhotoList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, carPhotoList);
        } catch (Exception e) {
            logger.error("获取车型图片异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_VEHICLE_EXPAND, expiration = 3600 * 24)
    @Override
    public PtResult getExpandVehicleByExample(ExpandVehicleExample example
            , @ParameterValueKeyProvider String cacheKey) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            ExpandVehicleMapper mapper = dbProxy.getMapper(ExpandVehicleMapper.class);
            List<ExpandVehicle> expandVehicleList = mapper.selectByExample(example);

            if (expandVehicleList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, expandVehicleList);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult setExpandVehicle(ExpandVehicle vehicle) {
        if (vehicle.getId() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER, null, null);
        }

        DbProxy proxy = dbManager.getDbProxy(true);

        try {
            ExpandVehicleMapper mapper = proxy.getMapper(ExpandVehicleMapper.class);
            int result = mapper.updateByPrimaryKeySelective(vehicle);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, result);
            }

            result = mapper.insertSelective(vehicle);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, result);
            }

            return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
        } catch (Exception e) {
            logger.error("设置扩展车型异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            proxy.close();
        }
    }
}
