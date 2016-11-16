package com.etong.pt.dao.impl;

import com.etong.pt.dao.PtUserDao;
import com.etong.pt.data.customer.PtCustomer;
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
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public class PtUserDaoImpl implements PtUserDao {
    public static final String DC_USER_INDEX = "dc_user";
    private static final Logger logger = LoggerFactory.getLogger(PtUserDaoImpl.class);
    public static final String DC_USERSYSTEM_INDEX = "DC_USERSYSTEM_INDEX";
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }


    @Override
    public PtResult add(PtUser record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        //检查用户是否存在
        PtResult ptResult = getByPhone(record.getF_phone());
        PtError ptError = ptResult.getPtError();

        if (ptError == PtCommonError.PT_ERROR_SUCCESS) {
            return new PtResult(PtCommonError.PT_ERROR_REG_REDUPLICATED, null, null);
        } else if (ptError == PtCommonError.PT_ERROR_DB) {
            return ptResult;
        }

        ptResult = dbIndexNum.generateIndexNum(DC_USER_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_mid(id.intValue());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_mid());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
    }

    //    @UpdateSingleCache(namespace = "pt:user", expiration = 3600)
    @Override
    public PtResult update(@ParameterValueKeyProvider @ParameterDataUpdateContent PtUser record) {
        PtUserExample example = new PtUserExample();
        example.or().andF_midEqualTo(record.getF_mid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

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

    //    @InvalidateSingleCache(namespace = "pt:user")
    @Override
    public PtResult delete(@ParameterValueKeyProvider Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

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
    public PtResult findAll() {
        PtUserExample example = new PtUserExample();
        //PtUserExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            example.setOrderByClause("f_mid asc");
            List<PtUser> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    //    @ReadThroughSingleCache(namespace = "pt:user", expiration = 3600)
    @Override
    public PtResult getById(@ParameterValueKeyProvider Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            PtUser record = mapper.selectByPrimaryKey(id);

            if (record == null) {
                return new PtResult(PtCommonError.PT_ERROR_USER_NOTEXIST, null, null);
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
    public PtResult getByPhone(String phone) {
        PtUserExample example = new PtUserExample();
        example.or().andF_phoneEqualTo(phone);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            List<PtUser> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_USER_NOTEXIST, null, null);
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
    public PtResult getOneByParam(PtUser param) {
        PtUserExample example = new PtUserExample();
        PtUserExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper mapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            if(param.getF_name() != null && !param.getF_name().isEmpty()) {
                criteria.andF_nameEqualTo(param.getF_name());
            }
            if(param.getF_phone() != null && !param.getF_phone().isEmpty()) {
                criteria.andF_phoneEqualTo(param.getF_phone());
            }
            if(param.getF_email() != null && !param.getF_email().isEmpty()) {
                criteria.andF_emailEqualTo(param.getF_email());
            }

            List<PtUser> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有查询到用户信息", null);
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
    public PtResult setPwdByPhone(String phone, String pwd, String salt) {
        PtUser ptUser = new PtUser();
        ptUser.setF_password(pwd);
        ptUser.setF_salt(salt);
        PtUserExample ptUserExample = new PtUserExample();
        ptUserExample.or().andF_phoneEqualTo(phone);
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper ptUserMapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            int result = ptUserMapper.updateByExampleSelective(ptUser, ptUserExample);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_USER_NOTEXIST, null, null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult modifyPassword(Integer id, String oldPwd, String newPwd) {
        PtUser ptUser = new PtUser();
        ptUser.setF_password(newPwd);
        PtUserExample ptUserExample = new PtUserExample();
        ptUserExample.or().andF_midEqualTo(id).andF_passwordEqualTo(oldPwd);
        DbProxy dbProxy = dbManager.getDbProxy(true);
        PtUserMapper ptUserMapper = dbProxy.getMapper(PtUserMapper.class);

        try {
            int result = ptUserMapper.updateByExampleSelective(ptUser, ptUserExample);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_PASSWORD, null, null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
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
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户数据更新失败", null);
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
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户数据插入失败", null);
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
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户资料更新失败", null);
                }
            } else {
                PtResult ptResult = dbIndexNum.generateIndexNum(PtCustomerDaoImpl.DC_CUSTINFO_INDEX);

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                long custId = ptResult.getObject();
                customer.setF_ciid(custId);
                int result = customerMapper.insertSelective(customer);

                if (result < 1) {
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户资料插入失败", null);
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

        if (result > 0) {
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        }

        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "用户归属数据更新失败", null);
    }
}
