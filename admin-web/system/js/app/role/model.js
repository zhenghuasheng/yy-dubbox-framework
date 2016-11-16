var RoleModel = Class.extend({
    data: [],
    selRole: null,
    selUser: [],
    userCache: {},
    roleResource: [],
    resourceCache: [],

    init: function () {
    },

    setRole: function (id, role) {
        this.data[id] = role;
    },

    getRole: function (id) {
        return this.data[id];
    },

    addSelUser: function (id) {
        this.selUser[id] = this.userCache[id];
    },

    removeSelUser: function (id) {
        this.selUser[id] = undefined;
    },

    getSeluser: function (id) {
        return this.selUser[id];
    },

    clearSelUser: function () {
        this.selUser = [];
    },

    addUser: function (user) {
        this.userCache[user.f_mid] = user;
    },

    saveSelUser: function (callback) {
        var userRoleList = [];

        for (var user in this.selUser) {
            var userRole = {};
            userRole.mid = this.selUser[user].f_mid;
            userRole.rlid = this.selRole.rlid;
            userRole.stid = this.selRole.stid;
            userRole.available = 1;
            userRoleList[userRoleList.length] = userRole;
        }

        $.ajax({
            type: 'post',
            url: global.auth_url + 'userrole/list',
            data: JSON.stringify(userRoleList),
            model: this,
            contentType: 'application/json',

            success: function (result) {
                callback(result);
            }
        });
    },

    removeUserRole: function (id, callback) {
        $.ajax({
            type: 'delete',
            url: global.auth_url + 'userrole/' + id,
            model: this,
            contentType: 'application/json',

            success: function (result) {
                callback(result);
            }
        });
    },

    loadRoleResource: function (roleId, callback) {
        if (this.roleResource[roleId] != undefined) {
            callback();
            return;
        }

        $.ajax({
            type: 'get',
            url: global.auth_url + 'role/resource/' + roleId + "/0/-1",
            model: this,
            contentType: 'application/json',

            success: function (result) {
                if (result.succeed || result.ptError == 'PT_ERROR_NODATA') {
                    this.model.roleResource[roleId] = [];

                    if (result.object != undefined) {
                        for (var i = 0; i < result.object.length; ++i) {
                            this.model.roleResource[roleId][result.object[i].rsid] = result.object[i];
                        }
                    }

                    callback();
                } else {
                    var dlg = new ModalDlg();
                    dlg.show('roleResourceResult', '角色资源获取失败', '失败原因：' + result.description);
                }
            }
        });
    },

    getRoleResource: function(resourceId) {
        if (this.selRole == undefined) {
            return null;
        }

        var roleResourceList = this.roleResource[this.selRole.rlid];

        if (roleResourceList == undefined) {
            return null;
        }

        return roleResourceList[resourceId];
    },

    addRoleResource: function (id, callback) {
        var resource = roleController.roleModel.resourceCache[id];

        var roleResource = {
            rlid : roleController.roleModel.selRole.rlid,
            rsid : resource.id,
            stid : global.system,
            available: true
        };

        $.ajax({
            type: 'put',
            url: global.auth_url + 'role/resource',
            model: this,
            data: JSON.stringify(roleResource),
            contentType: 'application/json',

            success: function (result) {
                if (result.succeed) {
                    roleResource.id = result.object;

                    if (this.model.roleResource[roleResource.rlid] == undefined) {
                        this.model.roleResource[roleResource.rlid] = [];
                    }

                    this.model.roleResource[roleResource.rlid][resource.id] = roleResource;
                    callback();
                } else {
                    var dlg = new ModalDlg();
                    dlg.show('addRoleResourceResult', '角色资源添加失败', '失败原因：' + result.description);
                }
            }
        });
    },

    delRoleResource: function(roleResource, callback) {
        roleResource.available = false;

        $.ajax({
            type: 'put',
            url: global.auth_url + 'role/resource',
            model: this,
            data: JSON.stringify(roleResource),
            contentType: 'application/json',

            success: function (result) {
                if (result.succeed) {
                    this.model.roleResource[this.model.selRole.rlid][roleResource.rsid] = undefined;
                    callback();
                } else {
                    var dlg = new ModalDlg();
                    dlg.show('delRoleResourceResult', '角色资源删除失败', '失败原因：' + result.description);
                }
            }
        });
    },

});
