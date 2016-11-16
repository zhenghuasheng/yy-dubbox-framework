/**
 * Created by wunan on 15/12/30.
 */
package com.etong.pt.data.service;

import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.customer.PtCustomerExample;
import com.etong.pt.data.customer.PtCustomerMapper;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.user.PtUserExample;
import com.etong.pt.data.user.PtUserMapper;
import com.etong.pt.data.user.detail.UserDetail;
import com.etong.pt.data.user.detail.UserDetailExample;
import com.etong.pt.data.user.detail.UserDetailMapper;
import com.etong.pt.data.user.system.UserSystem;
import com.etong.pt.data.user.system.UserSystemExample;
import com.etong.pt.data.user.system.UserSystemMapper;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.data.vehicle.PtVehicleExample;
import com.etong.pt.data.vehicle.PtVehicleMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDataImpl implements UserData {
    private static final Logger logger = LoggerFactory.getLogger(UserDataImpl.class);
    public static final String DC_USER_INDEX = "dc_user";
    public static final String DC_USERSYSTEM_INDEX = "dc_usersystem_index";
    public static final String DC_CUSTINFO_INDEX = "dc_custinfo";
    public static final String DC_VEHICLE_INDEX = "dc_vehicle";
    public static final String MEM_USER = "MEM:USER";
    public static final String MEM_CUST = "MEM:CUST";
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult getUser(PtUserExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper ptUserMapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            List<PtUser> userList = ptUserMapper.selectByExample(example);

            if (userList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有查询到用户数据", null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, userList);
            }
        } catch (Exception e) {
            logger.error("获取用户数据异常:{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取用户数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_USER, expiration = 3600)
    @Override
    public PtResult getUser(PtUserExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getUser(example);
    }

    @Override
    public PtResult getUserDetail(UserDetailExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        UserDetailMapper userDetailMapper = dbProxy.getMapper(UserDetailMapper.class);

        try {
            List<UserDetail> userList = userDetailMapper.selectByExample(example);

            if (userList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有查询到用户详细数据", null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, userList);
            }
        } catch (Exception e) {
            logger.error("获取用户详细数据异常:{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取用户详细数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult putUserDetail(UserDetail userDetail) {
        PtUser user = new PtUser();
        user.setF_mid(userDetail.getMid());
        user.setF_phone(userDetail.getLoginid());
        user.setF_name(userDetail.getUsername());
        user.setF_email(userDetail.getEmail());
        String password = userDetail.getPassword();

        if ((password != null) && !password.isEmpty()) {
            user.setF_password(userDetail.getPassword());
            user.setF_salt(userDetail.getSalt());
        }

        user.setF_verify(Boolean.FALSE);
        PtCustomer customer = new PtCustomer();
        customer.setF_ciid(userDetail.getCiid());
        customer.setF_name(userDetail.getName());
        customer.setF_sex(userDetail.getSex());
        customer.setF_marriage(userDetail.getMarriage());
        customer.setF_phone(userDetail.getPhone());
        customer.setF_idcard(userDetail.getIdcard());
        customer.setF_address(userDetail.getAddress());
        customer.setF_house(userDetail.getHouse());
        customer.setF_company(userDetail.getCompany());
        customer.setF_position(userDetail.getPosition());
        customer.setF_cardtype(userDetail.getCardtype());
        customer.setF_residence(userDetail.getResidence());
        customer.setF_education(userDetail.getEducation());
        customer.setF_homephone(userDetail.getHomephone());
        customer.setF_technicaltitle(userDetail.getTechnicaltitle());
        customer.setF_email(user.getF_email());
        customer.setF_unitaddress(userDetail.getUnitaddress());
        customer.setF_workinglife(userDetail.getWorkinglife());
        customer.setF_unitproperties(userDetail.getUnitproperties());
        customer.setF_system(userDetail.getSystem());
        customer.setF_source(userDetail.getSource());
        customer.setF_desc(userDetail.getDesc());
        customer.setF_genre(userDetail.getGenre());
        customer.setF_createid(userDetail.getCreateid());
        customer.setF_creator(userDetail.getCreator());
        customer.setF_editorid(userDetail.getEditorid());
        customer.setF_editor(userDetail.getEditor());
        customer.setF_initdate(userDetail.getInitdate());
        customer.setF_updatedate(userDetail.getUpdatedate());
        customer.setF_sysmid(userDetail.getSysmid());
        customer.setF_qq(userDetail.getQq());
        customer.setF_weixin(userDetail.getWeixin());
        customer.setF_mid(user.getF_mid());

        DbProxy dbProxy = dbManager.getDbProxy(false);

        try {
            PtUserMapper userMapper = dbProxy.getMapper(PtUserMapper.class);

            if (user.getF_mid() != null) {
                int result = userMapper.updateByPrimaryKeySelective(user);

                if (result < 1) {
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                            , "用户数据更新失败", null);
                }
            } else {
                PtResult ptResult = dbIndexNum.generateIndexNum(DC_USER_INDEX);

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                long userId = ptResult.getObject();
                user.setF_mid((int) userId);
                int result = userMapper.insertSelective(user);

                if (result < 1) {
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                            , "用户数据插入失败", null);
                }

                ptResult = updateUserSystem(dbProxy, userDetail.getSystem(), user.getF_mid());

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }
            }

            customer.setF_mid(user.getF_mid());
            PtCustomerMapper customerMapper = dbProxy.getMapper(PtCustomerMapper.class);

            if (customer.getF_ciid() != null) {
                int result = customerMapper.updateByPrimaryKeySelective(customer);

                if (result < 1) {
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                            , "用户资料更新失败", null);
                }
            } else {
                PtResult ptResult = dbIndexNum.generateIndexNum(DC_CUSTINFO_INDEX);

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                long custId = ptResult.getObject();
                customer.setF_ciid(custId);
                int result = customerMapper.insertSelective(customer);

                if (result < 1) {
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                            , "用户资料插入失败", null);
                }
            }

            dbProxy.commit();
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置用户详细数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    private PtResult updateUserSystem(DbProxy dbProxy, String system, int userId) {
        UserSystem userSystem = new UserSystem();
        userSystem.setMid(userId);
        userSystem.setStid(system);
        userSystem.setLogintime((int) (new Date().getTime() / 1000));
        UserSystemExample example = new UserSystemExample();
        example.or().andStidEqualTo(system).andMidEqualTo(userId);
        UserSystemMapper userSystemMapper = dbProxy.getMapper(UserSystemMapper.class);
        int count = userSystemMapper.countByExample(example);

        if (count > 0) {
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(DC_USERSYSTEM_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        long id = ptResult.getObject();
        userSystem.setUsid(id);
        int result = userSystemMapper.insert(userSystem);

        if (result < 1) {
            return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                    , "用户归属数据更新失败", null);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
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

    @ReadThroughSingleCache(namespace = MEM_CUST, expiration = 3600)
    @Override
    public PtResult getCustomer(PtCustomerExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getCustomer(example);
    }

    @Override
    public PtResult putCustomer(PtCustomer customer) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtCustomerMapper mapper = dbProxy.getMapper(PtCustomerMapper.class);

        try {
            int result = mapper.updateByPrimaryKeySelective(customer);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "客户资料更新失败", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("客户资料更新异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置客户资料异常", null);
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

    @ReadThroughSingleCache(namespace = "MEM:USERCAR", expiration = 3600)
    @Override
    public PtResult getVehicle(PtVehicleExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getVehicle(example);
    }

    @Override
    public PtResult addVehicle(PtVehicle vehicle) {
        if ((vehicle.getF_ciid() == null) || (vehicle.getF_ciid() < 1)) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "添加客户资料需要设置客户ID参数", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(DC_VEHICLE_INDEX);

        if (!ptResult.isSucceed()) {
            logger.error("客户车辆索引生成失败，车款ID：{}", vehicle.getF_vid());
            return ptResult;
        }

        Long id = ptResult.getObject();
        vehicle.setF_dvid(id);
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

        try {
            int result = mapper.insertSelective(vehicle);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                        , "客户车辆数据添加失败", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, id);
        } catch (Exception e) {
            logger.error("客户车辆数据添加异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "客户车辆数据添加异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult putVehicle(PtVehicle vehicle) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtVehicleMapper mapper = dbProxy.getMapper(PtVehicleMapper.class);

        try {
            int result = mapper.updateByPrimaryKeySelective(vehicle);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT
                        , "客户车辆资料更新失败", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("客户车辆数据更新异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "客户车辆数据更新异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getUserInfo(PtUserExample example) {
        PtResult ptResult = getUser(example);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<PtUser> userList = ptResult.getObject();
        List<UserInfo> userInfos = new ArrayList<>(userList.size());

        for (PtUser user : userList) {
            PtCustomerExample custExample = new PtCustomerExample();
            custExample.or().andF_midEqualTo(user.getF_mid());
            ptResult = getCustomer(custExample);

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            List<PtCustomer> custList = ptResult.getObject();
            PtCustomer cust = custList.get(0);
            PtVehicleExample vehicleExample = new PtVehicleExample();
            vehicleExample.or().andF_ciidEqualTo(cust.getF_ciid());
            ptResult = getVehicle(vehicleExample);

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            List<PtVehicle> vehicles = ptResult.getObject();
            UserInfo userInfo = new UserInfo();
            userInfo.setUser(user);
            userInfo.setCust(cust);
            userInfo.setVehicles(vehicles);
            userInfos.add(userInfo);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, userInfos);
    }

    @Override
    public PtResult addUserInfo(UserInfo userInfo) {
        PtResult ptResult = checkExist(userInfo.getUser());

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        DbProxy dbProxy = dbManager.getDbProxy(false);

        try {
            ptResult = dbIndexNum.generateIndexNum(DC_USER_INDEX, dbProxy);

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            Long userId = ptResult.getObject();
            PtUser user = userInfo.getUser();
            user.setF_mid(userId.intValue());
            PtUserMapper userMapper = dbProxy.getMapper(PtUserMapper.class);
            int result = userMapper.insertSelective(user);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户数据插入失败", null);
            }

            ptResult = dbIndexNum.generateIndexNum(DC_CUSTINFO_INDEX, dbProxy);

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            PtCustomer cust = userInfo.getCust();

            if (cust == null) {
                cust = new PtCustomer();
                cust.setF_phone(user.getF_phone());
                cust.setF_email(user.getF_email());
            }

            Long custId = ptResult.getObject();
            cust.setF_ciid(custId);
            cust.setF_updatedate((int) (new Date().getTime() / 1000));
            PtCustomerMapper custMapper = dbProxy.getMapper(PtCustomerMapper.class);
            result = custMapper.insertSelective(cust);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户资料数据插入失败", null);
            }

            List<PtVehicle> vehicles = userInfo.getVehicles();

            if (vehicles != null) {
                for (PtVehicle vehicle : vehicles) {
                    vehicle.setF_ciid(custId);
                    ptResult = dbIndexNum.generateIndexNum(DC_VEHICLE_INDEX, dbProxy);

                    if (!ptResult.isSucceed()) {
                        return ptResult;
                    }

                    Long vehicleId = ptResult.getObject();
                    vehicle.setF_dvid(vehicleId);
                    PtVehicleMapper vehicleMapper = dbProxy.getMapper(PtVehicleMapper.class);
                    result = vehicleMapper.insertSelective(vehicle);

                    if (result < 1) {
                        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户车辆数据插入失败", null);
                    }
                }
            }

            dbProxy.commit();
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, "用户数据插入异常", null);
        } finally {
            dbProxy.close();
        }
    }

    private PtResult checkExist(PtUser user) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserExample example = new PtUserExample();

        if (user.getF_name() != null) {
            example.or().andF_nameEqualTo(user.getF_name());
        }

        if (user.getF_phone() != null) {
            example.or().andF_phoneEqualTo(user.getF_phone());
        }

        if (user.getF_email() != null) {
            example.or().andF_emailEqualTo(user.getF_email());
        }

        try {
            PtUserMapper userMapper = dbProxy.getMapper(PtUserMapper.class);
            int count = userMapper.countByExample(example);

            if (count == 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_REG_REDUPLICATED, "用户已经存在", null);
            }
        } catch (Exception e) {
            return new PtResult(PtCommonError.PT_ERROR_DB, "查询用户是否存在出现异常", null);
        } finally {
            dbProxy.close();
        }
    }
}
