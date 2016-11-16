/**
 * Created by wunan on 15-12-11.
 */
package com.etong.data.auth.userrole;

import java.io.Serializable;

public class UserRoleDetail implements Serializable {
    private Long id;
    private Long userId;
    private Long roleId;
    private String name;
    private String system;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
