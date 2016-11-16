/**
 * Created by wunan on 15-11-23.
 */
package com.etong.data.auth.service;

import com.etong.data.auth.resource.Resource;
import com.etong.data.auth.resource.ResourceExample;
import com.etong.data.auth.role.Role;
import com.etong.data.auth.role.RoleExample;
import com.etong.data.auth.roleresource.RoleResource;
import com.etong.data.auth.roleresource.RoleResourceExample;
import com.etong.data.auth.session.Session;
import com.etong.data.auth.session.SessionExample;
import com.etong.data.auth.userresource.UserResource;
import com.etong.data.auth.userresource.UserResourceExample;
import com.etong.data.auth.userrole.UserRole;
import com.etong.data.auth.userrole.UserRoleExample;
import com.etong.pt.utility.PtResult;

import java.util.List;

public interface AuthData {
    PtResult getSession(SessionExample example);
    PtResult getSession(SessionExample example, String cacheKey);
    PtResult putSession(Session session);

    PtResult getResource(ResourceExample example);
    PtResult getResource(ResourceExample example, String cacheKey);
    PtResult putResource(Resource resource);

    PtResult getRole(RoleExample example);
    PtResult getRole(RoleExample example, String cacheKey);
    PtResult putRole(Role role);

    PtResult getRoleResource(RoleResourceExample example);
    PtResult getRoleResource(RoleResourceExample example, String cacheKey);
    PtResult putRoleResource(RoleResource roleResource);
    PtResult delRoleResource(RoleResourceExample example);

    PtResult getUserResource(UserResourceExample example);
    PtResult getUserResource(UserResourceExample example, String cacheKey);
    PtResult putUserResource(UserResource userResource);

    PtResult getUserRole(UserRoleExample example);
    PtResult getUserRole(UserRoleExample example, String cacheKey);
    PtResult putUserRole(UserRole userRole);
    PtResult addUserRole(List<UserRole> userRoleList);
    PtResult delUserRole(UserRoleExample example);

    PtResult findUserResource(com.etong.data.auth.view.UserResourceExample example);
    PtResult findUserResource(com.etong.data.auth.view.UserResourceExample example, String cacheKey);
}
