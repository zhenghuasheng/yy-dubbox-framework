/**
 * Created by wunan on 15-11-5.
 */
package com.etong.filter;

public class AuthContext {
    public final static String SYSTEM_ADMIN = "1";

    private static AuthContext ourInstance = new AuthContext();
    private ThreadLocal<String> token = new ThreadLocal<String>();
    private ThreadLocal<Long> userId = new ThreadLocal<Long>();
    private ThreadLocal<String> system = new ThreadLocal<String>();

    public static AuthContext getInstance() {
        return ourInstance;
    }

    public String getToken() {
        return token.get();
    }

    public void setToken(String token) {
        this.token.set(token);
    }

    public Long getUserId() {
        return userId.get();
    }

    public void setUserId(Long userId) {
        this.userId.set(userId);
    }

    public String getSystem() {
        return system.get();
    }

    public void setSystem(String system) {
        this.system.set(system);
    }

    public void reset() {
        this.token.set(null);
        this.userId.set(null);
        this.system.set(null);
    }

    private AuthContext() {
    }
}
