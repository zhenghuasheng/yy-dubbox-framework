/**
 * Created by wunan on 15-10-10.
 */
package com.etong.dc.auth;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.container.utility.CacheKey;
import com.etong.data.auth.resource.Resource;
import com.etong.data.auth.resource.ResourceExample;
import com.etong.data.auth.role.Role;
import com.etong.data.auth.role.RoleExample;
import com.etong.data.auth.roleresource.RoleResource;
import com.etong.data.auth.roleresource.RoleResourceExample;
import com.etong.data.auth.service.AuthData;
import com.etong.data.auth.session.Session;
import com.etong.data.auth.session.SessionExample;
import com.etong.data.auth.userrole.UserRole;
import com.etong.data.auth.userrole.UserRoleExample;
import com.etong.data.auth.view.UserResource;
import com.etong.data.auth.view.UserResourceExample;
import com.etong.filter.AuthContext;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("auth")
@Produces({ContentType.APPLICATION_JSON_UTF_8})

public class AuthServiceImpl implements AuthService {
    public static final String CLAIMS_EXTRA_DATA = "extraData";
    public static final String CLAIMS_USER_ID = "userId";
    public static final String CLAIMS_SYSTEM_ID = "systemId";
    public static final String CATEGORY_RESOURCE = "RESOURCE";
    public static final String CATEGORY_ROLE = "ROLE";
    public static final String CATEGORY_USERROLE = "USERROLE";
    public static final String CATEGORY_ROLERESOURCE = "ROLERESOURCE";
    public static final String CATEGORY_AUTH = "AUTH";
    private static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private AuthData authData;
    private CacheKey cacheKey;

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public void setCacheKey(CacheKey cacheKey) {
        this.cacheKey = cacheKey;
    }

    @POST
    @Path("token")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult createToken(TokenParam tokenParam) {
        String issuer = tokenParam.getIssuer();
        PtResult ptResult = createJsonWebKey(issuer);

        if (!ptResult.isSucceed()) {
            logger.error("创建令牌密钥失败, param:{}, result:{}"
                    , tokenParam.toString(), ptResult.toString());
            return ptResult;
        }

        RsaJsonWebKey rsaJsonWebKey = ptResult.getObject();
        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(issuer);  // who creates the token and signs it
        claims.setAudience(tokenParam.getAudience()); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(tokenParam.getExpireTime()); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
//        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(tokenParam.getSubject()); // the subject/principal is whom the token is about
        claims.setClaim(CLAIMS_EXTRA_DATA, tokenParam.getExtraData());
        claims.setClaim(CLAIMS_USER_ID, tokenParam.getUserId());
        claims.setClaim(CLAIMS_SYSTEM_ID, tokenParam.getSystem());
        claims.setGeneratedJwtId();

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        try {
            String jwt = jws.getCompactSerialization();
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jwt);
        } catch (JoseException e) {
            logger.error("创建令牌失败：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_GEN_TOKEN, "生成令牌错误", null);
        }
    }

    @GET
    @Path("token/{token}")
    @Override
    public PtResult parseToken(@PathParam("token") String token) {
        PtResult ptResult = parseJwt(token);

        if (!ptResult.isSucceed()) {
            logger.warn("令牌解析失败:{}", token);
            return ptResult;
        }

        JwtClaims jwtClaims = ptResult.getObject();
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null
                , jwtClaims.getClaimValue(CLAIMS_EXTRA_DATA));
    }

    @GET
    @Path("check/{token}/{resource}")
    @Override
    public PtResult checkAuth(@PathParam("token") String token
            , @PathParam("resource") String resource) {
        PtResult ptResult = parseJwt(token);

        if (!ptResult.isSucceed()) {
            logger.warn("令牌校验失败:{}", token);
            return ptResult;
        }

        JwtClaims jwtClaims = ptResult.getObject();
        Long userId = (Long) jwtClaims.getClaimValue(CLAIMS_USER_ID);
        String system = (String) jwtClaims.getClaimValue(CLAIMS_SYSTEM_ID);

        if ((userId == null) || (system == null)) {
            logger.warn("无法验证令牌权限，没有设置用户和系统标识，" +
                    "token:{}, resource:{}", token, resource);
            return new PtResult(PtCommonError.PT_ERROR_NOAUTH
                    , "用户或者系统标识为空", null);
        }

        UserResourceExample example = new UserResourceExample();
        example.or().andMidEqualTo(userId).andStidEqualTo(
                system).andRspathEqualTo(resource);
        ptResult = authData.findUserResource(example, cacheKey.getKey(
                system, CATEGORY_AUTH, userId.toString(), resource));

        if (!ptResult.isSucceed()) {
            logger.warn("获取用户对应资源数据失败，user:{}, resource:{}, result:{}"
                    , userId, resource, ptResult.toString());
            return ptResult;
        }

        List<UserResource> userResourceList = ptResult.getObject();
        logger.debug("用户权限获取结果 user:{}, resource:{}, available:{}"
                , userId, resource, userResourceList.get(0).toString());

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null
                , userResourceList.get(0));
    }

    @GET
    @Path("list/{type}")
    @Override
    public PtResult getAuth(@PathParam("type") int type) {
        Long userId = AuthContext.getInstance().getUserId();
        String system = AuthContext.getInstance().getSystem();
        UserResourceExample example = new UserResourceExample();
        example.or().andMidEqualTo(userId)
                .andStidEqualTo(system)
                .andTypeEqualTo((byte)type)
                .andAvailableEqualTo((byte)1);

        example.setOrderByClause("f_vieworder");
        PtResult ptResult = authData.findUserResource(example
                , cacheKey.getKey(system, CATEGORY_AUTH));

        if (!ptResult.isSucceed()) {
            logger.warn("获取用户资源数据失败, user:{}, system:{}, result:{}"
                    , userId, system, ptResult.toString());
        }

        return ptResult;
    }

    @GET
    @Path("resource/{start}/{limit}")
    @Override
    public PtResult getResource(@PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        String system = AuthContext.getInstance().getSystem();
        //(stid = system) and (delete is not null) or (stid = system) and (delete <> true)
        ResourceExample example = new ResourceExample();

        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            example.or().andStidEqualTo(system).andDeleteIsNull();
            ResourceExample.Criteria criteria = example.createCriteria();
            criteria.andStidEqualTo(system).andDeleteNotEqualTo(Boolean.TRUE);
            example.or(criteria);
        } else {
            example.or().andDeleteIsNull();
            example.or().andDeleteNotEqualTo(Boolean.TRUE);
        }

        example.setLimitClause(start, limit);
        return authData.getResource(example, cacheKey.getKeyByPage(
                system, CATEGORY_RESOURCE, start, limit));
    }

    @POST
    @Path("resource")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult addResource(Resource resource) {
        if ((resource.getRspath() == null)
                || (resource.getName() == null)) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "资源必要参数没有设置", null);
        }

        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能添加本系统资源
        if (!AuthContext.SYSTEM_ADMIN.equals(system)
                || (resource.getStid() == null)) {
            resource.setStid(system);
        }

        return changeCacheVer(authData.putResource(resource)
                , system, CATEGORY_RESOURCE);
    }

    @PUT
    @Path("resource")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult putResource(Resource resource) {
        if (resource.getId() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "资源必要参数没有设置", null);
        }

        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能更新本系统资源
        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            resource.setStid(null);
        }

        return changeCacheVer(authData.putResource(resource)
                , system, CATEGORY_RESOURCE);
    }

    @GET
    @Path("role/{start}/{limit}")
    @Override
    public PtResult getRole(@PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        String system = AuthContext.getInstance().getSystem();
        RoleExample example = new RoleExample();

        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            example.or().andStidEqualTo(system);
        }

        example.setLimitClause(start, limit);
        return authData.getRole(example, cacheKey.getKeyByPage(
                system, CATEGORY_ROLE, start, limit));
    }

    @POST
    @Path("role")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult addRole(Role role) {
        if (role.getName() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "角色必要参数没有设置", null);
        }

        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能添加本系统角色
        if (!AuthContext.SYSTEM_ADMIN.equals(system)
                || (role.getStid() == null)) {
            role.setStid(system);
        }

        return changeCacheVer(authData.putRole(role), system, CATEGORY_ROLE);
    }

    @PUT
    @Path("role")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult putRole(Role role) {
        if (role.getRlid() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "角色必要参数没有设置", null);
        }

        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能设置本系统角色
        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            role.setStid(null);
        }

        return changeCacheVer(authData.putRole(role), system, CATEGORY_ROLE);
    }

    @GET
    @Path("userrole/{role}/{start}/{limit}")
    @Override
    public PtResult getUserRole(@PathParam("role") Long roleId
            , @PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        UserRoleExample example = new UserRoleExample();
        String system = AuthContext.getInstance().getSystem();

        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            example.or().andStidEqualTo(system).andRlidEqualTo(roleId)
                    .andRoleMidEqualToUserRoleMid();
        } else {
            example.or().andRlidEqualTo(roleId)
                    .andRoleMidEqualToUserRoleMid();
        }

        example.setLimitClause(start, limit);
        return authData.getUserRole(example, cacheKey.getKeyByPage(system
                , CATEGORY_USERROLE, start, limit, roleId.toString()));
    }

    @POST
    @Path("userrole")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult addUserRole(UserRole userRole) {
        if ((userRole.getRlid() == null)
                || (userRole.getMid() == null)) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "添加角色成员的必要参数没有设置", null);
        }

        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能添加本系统角色成员
        if (!AuthContext.SYSTEM_ADMIN.equals(system)
                || (userRole.getStid() == null)) {
            userRole.setStid(system);
        }

        return changeCacheVer(authData.putUserRole(userRole)
                , system, CATEGORY_USERROLE, CATEGORY_AUTH);
    }

    @POST
    @Path("userrole/list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult addUserRole(List<UserRole> userRoleList) {
        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能添加本系统角色成员
        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            for (UserRole userRole : userRoleList) {
                userRole.setStid(system);
            }
        }

        return changeCacheVer(authData.addUserRole(userRoleList)
                , system, CATEGORY_USERROLE, CATEGORY_AUTH);
    }

    @DELETE
    @Path("userrole/{id}")
    @Override
    public PtResult delUserRole(@PathParam("id") Long id) {
        String system = AuthContext.getInstance().getSystem();
        UserRoleExample example = new UserRoleExample();
        example.or().andIdEqualTo(id);
        return changeCacheVer(authData.delUserRole(example)
                , system, CATEGORY_USERROLE, CATEGORY_AUTH);
    }

    @GET
    @Path("role/resource/{role}/{start}/{limit}")
    @Override
    public PtResult getRoleResource(@PathParam("role") Long roleId
            , @PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        RoleResourceExample example = new RoleResourceExample();
        String system = AuthContext.getInstance().getSystem();

        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            example.or().andStidEqualTo(system).andRlidEqualTo(roleId);
        } else {
            example.or().andRlidEqualTo(roleId);
        }

        if (limit != -1) {
            example.setLimitClause(start, limit);
        }

        return authData.getRoleResource(example, cacheKey.getKeyByPage(system
                , CATEGORY_ROLERESOURCE, start, limit, roleId.toString()));
    }

    @PUT
    @Path("role/resource")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult putRoleResource(RoleResource roleResource) {
        if ((roleResource.getRlid() == null)
                || (roleResource.getRsid() == null)) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "没有设置添加角色资源的必要参数", null);
        }

        String system = AuthContext.getInstance().getSystem();

        //非超级管理员,只能增加本系统的权限
        if (!AuthContext.SYSTEM_ADMIN.equals(system)
                || (roleResource.getStid() == null)) {
            roleResource.setStid(system);
        }

        return changeCacheVer(authData.putRoleResource(roleResource)
                , system, CATEGORY_ROLERESOURCE, CATEGORY_AUTH);
    }

    @DELETE
    @Path("role/resource/{id}")
    @Override
    public PtResult delRoleResource(@PathParam("id") Long id) {
        String system = AuthContext.getInstance().getSystem();
        RoleResourceExample example = new RoleResourceExample();
        example.or().andIdEqualTo(id);
        return changeCacheVer(authData.delRoleResource(example)
                , system, CATEGORY_ROLERESOURCE, CATEGORY_AUTH);
    }

    private PtResult createJsonWebKey(String issuer) {
        if ((issuer == null) || issuer.isEmpty()) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER, null, null);
        }

        SessionExample example = new SessionExample();
        example.or().andAdminEqualTo(issuer);
        PtResult ptResult = authData.getSession(example);

        if (ptResult.isSucceed()) {
            List<Session> sessionList = ptResult.getObject();

            try {
                RsaJsonWebKey rsaJsonWebKey = (RsaJsonWebKey)
                        JsonWebKey.Factory.newJwk(sessionList.get(0).getJwkjson());
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, rsaJsonWebKey);
            } catch (JoseException e) {
                logger.error("解析令牌密钥失败:{}", e.getMessage());
                return new PtResult(PtCommonError.PT_ERROR_JSON_PARSE, "密钥解析失败", null);
            }
        }

        if (ptResult.getPtError() != PtCommonError.PT_ERROR_NODATA) {
            return ptResult;
        }

        try {
            RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            rsaJsonWebKey.setKeyId(issuer);
            Session session = new Session();
            session.setAdmin(issuer);
            session.setJwkjson(rsaJsonWebKey.toJson(
                    JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
            ptResult = authData.putSession(session);

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, rsaJsonWebKey);
        } catch (JoseException e) {
            logger.error("创建令牌密钥失败:{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_GEN_TOKEN, "生成密钥失败", null);
        }
    }

    private PtResult parseJwt(String jwt) {
        // Build a JwtConsumer that doesn't check signatures or do any validation.
        JwtConsumer firstPassJwtConsumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();

        try {
            JwtContext jwtContext = firstPassJwtConsumer.process(jwt);
            JwtClaims jwtClaims = jwtContext.getJwtClaims();
            String issuer = jwtClaims.getIssuer();
            SessionExample example = new SessionExample();
            example.or().andAdminEqualTo(issuer);
            PtResult ptResult = authData.getSession(example);

            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            List<Session> sessionList = ptResult.getObject();
            JsonWebKey jwk = JsonWebKey.Factory.newJwk(sessionList.get(0).getJwkjson());
            JwtConsumerBuilder consumerBuilder = new JwtConsumerBuilder()
                    .setExpectedIssuer(jwtClaims.getIssuer())
                    .setVerificationKey(jwk.getKey())
                    .setRequireExpirationTime()
                    .setAllowedClockSkewInSeconds(30);
            List<String> audienceList = jwtClaims.getAudience();

            if ((audienceList != null) && !audienceList.isEmpty()) {
                consumerBuilder.setExpectedAudience(audienceList.get(0));
            }

            JwtConsumer jwtConsumer = consumerBuilder.build();

            // Finally using the second JwtConsumer to actually validate the JWT. This operates on
            // the JwtContext from the first processing pass, which avoids redundant parsing/processing.
            jwtConsumer.processContext(jwtContext);
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jwtClaims);
        } catch (InvalidJwtException e) {
            logger.warn("令牌验证失败：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN, "令牌验证失败", null);
        } catch (MalformedClaimException e) {
            logger.error("令牌数据异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN, "令牌数据解析错误", null);
        } catch (JoseException e) {
            logger.error("令牌验证异常：{}", e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN, "令牌无法解析", null);
        }
    }

    private PtResult changeCacheVer(PtResult ptResult
            , String system, String... categorys) {
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        for (int i = 0; i < categorys.length; ++i) {
            cacheKey.changeVer(system, categorys[i]);
        }

        return ptResult;
    }
}
