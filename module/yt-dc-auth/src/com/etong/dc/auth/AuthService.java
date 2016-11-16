/**
 * Created by wunan on 15-10-10.
 * 权限认证服务接口
 */
package com.etong.dc.auth;

import com.etong.data.auth.resource.Resource;
import com.etong.data.auth.role.Role;
import com.etong.data.auth.roleresource.RoleResource;
import com.etong.data.auth.userrole.UserRole;
import com.etong.pt.utility.PtResult;

import java.util.List;

public interface AuthService {
    PtResult createToken(TokenParam tokenParam);
    PtResult parseToken(String token);
    PtResult checkAuth(String token, String resouce);
    PtResult getAuth(int type);

    PtResult getResource(Integer start, Integer limit);
    PtResult addResource(Resource resource);
    PtResult putResource(Resource resource);

    PtResult getRole(Integer start, Integer limit);
    PtResult addRole(Role role);
    PtResult putRole(Role role);

    PtResult getUserRole(Long roleId, Integer start, Integer limit);
    PtResult addUserRole(UserRole userRole);
    PtResult addUserRole(List<UserRole>userRoleList);
    PtResult delUserRole(Long id);

    PtResult getRoleResource(Long roleId, Integer start, Integer limit);
    PtResult putRoleResource(RoleResource roleResource);
    PtResult delRoleResource(Long id);
}
