var RoleController = Class.extend({
    roleView: null,
    roleModel: null,
    rolePage: null,
    userRolePage: null,
    userPage: null,
    resourcePage: null,

    init: function () {
        global.initMenu();
        this.roleView = new RoleView();
        this.roleModel = new RoleModel();

        this.rolePage = new PageData({
            url: global.auth_url + 'role',
            callback: this.showRole,
            prePageId: 'preRolePage',
            nextPageId: 'nextRolePage',
            curPageId: 'curRolePage'
        });

        this.rolePage.refresh();

        this.userRolePage = new PageData({
            callback: this.showUserRole,
            prePageId: 'preRoleUserPage',
            nextPageId: 'nextRoleUserPage',
            curPageId: 'curRoleUserPage'
        });

        this.userPage = new PageData({
            url: global.cust_url + 'list',
            callback: this.showUser,
            prePageId: 'preUserPage',
            nextPageId: 'nextUserPage',
            curPageId: 'curUserPage'
        });

        this.resourcePage = new PageData({
            url: global.auth_url + 'resource',
            callback: this.showResource,
            prePageId: 'preResourcePage',
            nextPageId: 'nextResourcePage',
            curPageId: 'curResourcePage'
        });

        this.initEvent();
    },

    initEvent: function () {
        $('#addRoleBtn').click(this.onClickAddRole);
        $('#addRoleUserBtn').click(function() {
            roleController.selRoleUser();
        });

        $('#saveUserRole').click(function () {
            roleController.roleModel.saveSelUser(roleController.afterSaveSelUser);
        });
    },

    showRole: function (result, page, limit) {
        if (result.succeed) {
            roleController.roleView.showRole(page, limit, result.object);
        }
    },

    showUserRole: function (result, page, limit) {
        if (result.succeed) {
            roleController.roleView.showUserRole(page, limit
                , result.object, roleController.roleModel.selRole);
        }
    },

    showResource: function (result, page, limit) {
        if (result.succeed) {
            roleController.roleView.showResource(page, limit, result.object);
        }
    },

    onClickAddRole: function () {
        global.setParameter('roleInfo', {editType: 1});
        window.location.href = "role_add.html";
    },

    onClickEditRole: function (id) {
        var role = roleController.roleModel.getRole(id);
        role.editType = 2;
        global.setParameter('roleInfo', role);
        window.location.href = "role_add.html";
    },

    onClickRoleUser: function (id) {
        roleController.roleModel.selRole = roleController.roleModel.getRole(id);
        roleController.roleView.resetUseRole(roleController.roleModel.selRole.name);
        roleController.userRolePage.refresh(0, global.auth_url + 'userrole/' + id);
    },

    onClickDelRole: function (id) {
        function afterDelete(result) {
            var dlg = new ModalDlg();

            if (result.succeed) {
                dlg.show('delResult', '删除结果', '资源已经移除到回收站中', afterDlgClose);
            } else {
                dlg.show('delResult', '删除结果', '资源移除失败：' + result.description);
            }
        }

        function afterDlgClose() {
            roleController.rolePage.refresh();
        }

        //roleController.roleModel.delResource(id, afterDelete);
    },

    onClickShowAuthDlg: function(id) {
        roleController.roleModel.selRole = roleController.roleModel.getRole(id);

        this.roleModel.loadRoleResource(id, function() {
            roleController.roleView.resetAuthRole();
            roleController.resourcePage.refresh(0);
            $('#authModal').modal('show');
        });
    },

    selRoleUser: function() {
        if (this.roleModel.selRole == undefined) {
            var dlg = new ModalDlg();
            dlg.show('selAlert', '提示', "尚未选择要添加成员的角色");
            return;
        }

        this.roleModel.clearSelUser();
        this.userPage.refresh();
        $('#userModal').modal('show');
    },

    showUser: function(result, page, limit) {
        if (result.succeed) {
            roleController.roleView.showUser(page, limit, result.object);
        }
    },

    onClickRemoveUser: function(id) {
        roleController.roleModel.removeUserRole(id
            , roleController.afterRemoveUserRole);
    },

    onClickSelUser: function (id, btn) {
        if (roleController.roleModel.getSeluser(id) == undefined) {
            roleController.roleModel.addSelUser(id);
            $(btn).removeClass('btn-info');
            $(btn).addClass('btn-danger');
        } else {
            roleController.roleModel.removeSelUser(id);
            $(btn).addClass('btn-info');
            $(btn).removeClass('btn-danger');
        }
    },

    afterSaveSelUser: function (result) {
        var dlg = new ModalDlg();

        if (result.succeed) {
            dlg.show('saveSelUser', '保存结果', '角色成员添加成功', function () {
                roleController.userRolePage.refresh();
                $('#userModal').modal('hide');
            });
        } else {
            dlg.show('saveSelUser', '保存结果', '角色成员添加失败：' + result.description);
        }
    },

    afterRemoveUserRole: function (result) {
        var dlg = new ModalDlg();

        if (result.succeed) {
            dlg.show('removeUserRole', '移除结果', '角色成员移除成功', function () {
                roleController.userRolePage.refresh();
            });
        } else {
            dlg.show('removeUserRole', '移除结果', '角色成员移除失败：' + result.description);
        }
    },

    onClickSelAuth: function (id, btn) {
        var roleResource = roleController.roleModel.getRoleResource(id);

        if (roleResource == undefined) {
            roleController.roleModel.addRoleResource(id, function() {
                $(btn).removeClass('btn-info');
                $(btn).addClass('btn-danger');
            });
        } else if (!roleResource.available) {
            roleController.roleModel.addRoleResource(id, function() {
                $(btn).removeClass('btn-warning');
                $(btn).addClass('btn-danger');
            });
        } else {
            roleController.roleModel.delRoleResource(roleResource, function() {
                $(btn).addClass('btn-warning');
                $(btn).removeClass('btn-danger');
            })
        }
    },
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                roleController = new RoleController();
            });
        }
    }
});
