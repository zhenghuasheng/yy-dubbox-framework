/**
 * Created by wunan on 15-11-23.
 */
package com.etong.data.auth.service;

import com.etong.data.auth.resource.Resource;
import com.etong.data.auth.resource.ResourceExample;
import com.etong.data.auth.resource.ResourceMapper;
import com.etong.data.auth.role.Role;
import com.etong.data.auth.role.RoleExample;
import com.etong.data.auth.role.RoleMapper;
import com.etong.data.auth.roleresource.RoleResource;
import com.etong.data.auth.roleresource.RoleResourceExample;
import com.etong.data.auth.roleresource.RoleResourceMapper;
import com.etong.data.auth.session.Session;
import com.etong.data.auth.session.SessionExample;
import com.etong.data.auth.session.SessionMapper;
import com.etong.data.auth.userresource.UserResource;
import com.etong.data.auth.userresource.UserResourceExample;
import com.etong.data.auth.userresource.UserResourceMapper;
import com.etong.data.auth.userrole.UserRole;
import com.etong.data.auth.userrole.UserRoleDetail;
import com.etong.data.auth.userrole.UserRoleExample;
import com.etong.data.auth.userrole.UserRoleMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthDataImpl implements AuthData {
    public static final String MEM_SESSION = "MEM:SESSION";
    public static final String MEM_RESOURCE = "MEM:RESOURCE";
    public static final String MEM_ROLE = "MEM:ROLE";
    public static final String MEM_ROLERESOURCE = "MEM:ROLERESOURCE";
    public static final String MEM_USERRESOURCE = "MEM:USERRESOURCE";
    public static final String MEM_USERROLE = "MEM:USERROLE";
    public static final String MEM_AUTH = "MEM:AUTH";
    private DbManager dbManager;
    private DbIndexNum dbIndexNum;
    private static Logger logger = LoggerFactory.getLogger(AuthDataImpl.class);

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    @Override
    public PtResult getSession(SessionExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            SessionMapper sessionMapper = dbProxy.getMapper(SessionMapper.class);
            List<Session> sessionList = sessionMapper.selectByExample(example);

            if (sessionList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, sessionList);
        } catch (Exception e) {
            logger.error("获取认证会话异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取认证会话异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_SESSION, expiration = 3600)
    @Override
    public PtResult getSession(SessionExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getSession(example);
    }

    @Override
    public PtResult putSession(Session session) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        SessionExample example = new SessionExample();
        example.or().andAdminEqualTo(session.getAdmin());

        try {
            SessionMapper sessionMapper = dbProxy.getMapper(SessionMapper.class);
            int result = sessionMapper.updateByExampleSelective(session, example);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            }

            PtResult ptResult = dbIndexNum.generateIndexNum("auth_session");

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            session.setPmsid((Long) ptResult.getObject());
            result = sessionMapper.insertSelective(session);

            if (result < 1) {
                logger.error("设置认证会话失败, admin: {}", session.getAdmin());
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("设置认证会话异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置认证会话异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getResource(ResourceExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            ResourceMapper resourceMapper = dbProxy.getMapper(ResourceMapper.class);
            List<Resource> resourceList = resourceMapper.selectByExample(example);

            if (resourceList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, resourceList);
        } catch (Exception e) {
            logger.error("获取资源数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取资源数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_RESOURCE, expiration = 3600)
    @Override
    public PtResult getResource(ResourceExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getResource(example);
    }

    @Override
    public PtResult putResource(Resource resource) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            ResourceMapper resourceMapper = dbProxy.getMapper(ResourceMapper.class);
            int result = resourceMapper.updateByPrimaryKeySelective(resource);

            if (result < 1) {
                PtResult ptResult = dbIndexNum.generateIndexNum("auth_resource");

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                resource.setId((Long) ptResult.getObject());

                if (resource.getItemid() == null) {
                    resource.setItemid(resource.getId().toString());
                }

                result = resourceMapper.insertSelective(resource);
            }

            if (result < 1) {
                logger.error("设置资源数据失败, ID: {}", resource.getRspath());
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("设置资源数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置资源数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getRole(RoleExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            RoleMapper roleMapper = dbProxy.getMapper(RoleMapper.class);
            List<Role> roleList = roleMapper.selectByExample(example);

            if (roleList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, roleList);
        } catch (Exception e) {
            logger.error("获取角色数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取角色数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_ROLE, expiration = 3600)
    @Override
    public PtResult getRole(RoleExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getRole(example);
    }

    @Override
    public PtResult putRole(Role role) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            RoleMapper roleMapper = dbProxy.getMapper(RoleMapper.class);
            int result = 0;

            if (role.getRlid() != null) {
                result = roleMapper.updateByPrimaryKeySelective(role);
            } else {
                PtResult ptResult = dbIndexNum.generateIndexNum("auth_role");

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                role.setRlid((Long) ptResult.getObject());
                result = roleMapper.insertSelective(role);
            }

            if (result < 1) {
                logger.error("设置角色数据失败, name: {}", role.getName());
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("设置角色数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置角色数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getRoleResource(RoleResourceExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            RoleResourceMapper roleResourceMapper = dbProxy.getMapper(RoleResourceMapper.class);
            List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);

            if (roleResourceList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有查询到角色资源数据", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, roleResourceList);
        } catch (Exception e) {
            logger.error("获取角色对应资源数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取角色对应资源数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_ROLERESOURCE, expiration = 3600)
    @Override
    public PtResult getRoleResource(RoleResourceExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getRoleResource(example);
    }

    @Override
    public PtResult putRoleResource(RoleResource roleResource) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            RoleResourceMapper roleResourceMapper = dbProxy.getMapper(RoleResourceMapper.class);
            int result = 0;

            if (roleResource.getId() != null) {
                result = roleResourceMapper.updateByPrimaryKeySelective(roleResource);
            } else {
                RoleResourceExample example = new RoleResourceExample();
                example.or().andRlidEqualTo(roleResource.getRlid())
                        .andRsidEqualTo(roleResource.getRsid());
                result = roleResourceMapper.updateByExampleSelective(roleResource, example);

                if (result < 1) {
                    PtResult ptResult = dbIndexNum.generateIndexNum("auth_roleresource");

                    if (!ptResult.isSucceed()) {
                        return ptResult;
                    }

                    roleResource.setId((Long) ptResult.getObject());
                    result = roleResourceMapper.insertSelective(roleResource);
                }
            }

            if (result < 1) {
                logger.error("设置角色资源失败, role: {}", roleResource.getRlid());
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, roleResource.getId());
        } catch (Exception e) {
            logger.error("设置角色对应资源数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置角色对应资源数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult delRoleResource(RoleResourceExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            RoleResourceMapper roleResourceMapper = dbProxy.getMapper(RoleResourceMapper.class);
            int result = roleResourceMapper.deleteByExample(example);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "删除角色资源错误", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("删除角色资源异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "删除角色资源异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getUserResource(UserResourceExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            UserResourceMapper userResourceMapper = dbProxy.getMapper(UserResourceMapper.class);
            List<UserResource> userResourceList = userResourceMapper.selectByExample(example);

            if (userResourceList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, userResourceList);
        } catch (Exception e) {
            logger.error("获取用户对应资源数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取用户对应资源数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_USERRESOURCE, expiration = 3600)
    @Override
    public PtResult getUserResource(UserResourceExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getUserResource(example);
    }

    @Override
    public PtResult putUserResource(UserResource userResource) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            UserResourceMapper userResourceMapper = dbProxy.getMapper(UserResourceMapper.class);
            int result = 0;

            if (userResource.getId() != null) {
                result = userResourceMapper.updateByPrimaryKeySelective(userResource);
            } else {
                PtResult ptResult = dbIndexNum.generateIndexNum("auth_userresource");

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                userResource.setId((Long) ptResult.getObject());
                result = userResourceMapper.insertSelective(userResource);
            }

            if (result < 1) {
                logger.error("设置用户对资源失败, userID: {}", userResource.getMid());
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("设置用户对应资源数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置用户对应资源数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult getUserRole(UserRoleExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            UserRoleMapper userRoleMapper = dbProxy.getMapper(UserRoleMapper.class);
            List<UserRoleDetail> userRoleList = userRoleMapper.selectDetailByExample(example);

            if (userRoleList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, userRoleList);
        } catch (Exception e) {
            logger.error("获取用户对应角色数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取用户对应角色数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_USERROLE, expiration = 3600)
    @Override
    public PtResult getUserRole(UserRoleExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return getUserRole(example);
    }

    @Override
    public PtResult putUserRole(UserRole userRole) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            UserRoleMapper userRoleMapper = dbProxy.getMapper(UserRoleMapper.class);
            int result = 0;

            if (userRole.getId() != null) {
                result = userRoleMapper.updateByPrimaryKeySelective(userRole);
            } else {
                PtResult ptResult = dbIndexNum.generateIndexNum("auth_userrole");

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                userRole.setId((Long) ptResult.getObject());
                result = userRoleMapper.insertSelective(userRole);
            }

            if (result < 1) {
                logger.error("用户对应角色插入失败, userID: {}", userRole.getMid());
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("设置用户对应角色数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置用户对应角色数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult addUserRole(List<UserRole> userRoleList) {
        DbProxy dbProxy = dbManager.getDbProxy(false);

        try {
            UserRoleMapper userRoleMapper = dbProxy.getMapper(UserRoleMapper.class);

            for (UserRole userRole : userRoleList) {
                PtResult ptResult = dbIndexNum.generateIndexNum("auth_userrole");

                if (!ptResult.isSucceed()) {
                    return ptResult;
                }

                userRole.setId((Long) ptResult.getObject());
                int result = userRoleMapper.insertSelective(userRole);

                if (result < 1) {
                    logger.error("用户对应角色插入失败, userID: {}", userRole.getMid());
                    return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
                }
            }

            dbProxy.commit();
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("设置用户对应角色数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "设置用户对应角色数据异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult delUserRole(UserRoleExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            UserRoleMapper userRoleMapper = dbProxy.getMapper(UserRoleMapper.class);
            int result = userRoleMapper.deleteByExample(example);

            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, "删除角色成员错误", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("删除角色成员异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "删除角色成员异常", null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult findUserResource(com.etong.data.auth.view.UserResourceExample example) {
        DbProxy dbProxy = dbManager.getDbProxy(true);

        try {
            com.etong.data.auth.view.UserResourceMapper userResourceMapper = dbProxy.getMapper(
                    com.etong.data.auth.view.UserResourceMapper.class);
            List<com.etong.data.auth.view.UserResource> userResourceList =
                    userResourceMapper.selectByExample(example);
            com.etong.data.auth.view.RoleResourceMapper roleResourceMapper = dbProxy.getMapper(
                    com.etong.data.auth.view.RoleResourceMapper.class);
            List<com.etong.data.auth.view.UserResource> roleResourceList =
                    roleResourceMapper.selectByExample(example);

            //使用用户权限覆盖角色权限
            for (com.etong.data.auth.view.UserResource userResource : userResourceList) {
                boolean find = false;

                for (com.etong.data.auth.view.UserResource roleResource : roleResourceList) {
                    if (userResource.getId().shortValue() == roleResource.getId().shortValue()) {
                        roleResource.setAvailable(userResource.getAvailable());
                        find = true;
                        break;
                    }
                }

                if (!find) {
                    roleResourceList.add(userResource);
                }
            }

            if (roleResourceList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有找到用户资源权限数据", null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, roleResourceList);
        } catch (Exception e) {
            logger.error("获取用户对应资源错误：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, "获取用户对应资源错误", null);
        } finally {
            dbProxy.close();
        }
    }

    @ReadThroughSingleCache(namespace = MEM_AUTH, expiration = 3600)
    @Override
    public PtResult findUserResource(com.etong.data.auth.view.UserResourceExample example
            , @ParameterValueKeyProvider String cacheKey) {
        return findUserResource(example);
    }
}
