var UserController = Class.extend({
    userView: null,
    userModel: null,
    userPage: null,
    userCarPage: null,

    init: function () {
        global.initMenu();
        this.userView = new UserView();
        this.userModel = new UserModel();

        this.userPage = new PageData({
            url: global.user_url + 'detail',
            callback: this.afterLoadUser,
            prePageId: 'preUserPage',
            nextPageId: 'nextUserPage',
            curPageId: 'curUserPage'
        });

        this.userCarPage = new PageData({
            callback: this.afterLoadUserCar,
            prePageId: 'preUserCarPage',
            nextPageId: 'nextUserCarPage',
            curPageId: 'curUserCarPage'
        });

        this.userPage.refresh();
        this.initEvent();
    },

    initEvent: function () {
        $('#addUserBtn').click(function() {
            userController.onAddUser();
        });

        $('#search').keydown(function(event) {
            if (event.keyCode == 13) {
                userController.userPage.option.condition = $('#search').val();
                userController.userPage.refresh(0);
            }
        });
    },

    afterLoadUser: function (result, page, limit) {
        if (result.succeed) {
            userController.userView.showUser(page, limit, result.object);
        }
    },

    loadUserCar: function(userId) {
        var user = this.userModel.getUser(userId);
        this.userModel.selUser = user;

        if (user == undefined) {
            var dlg = new ModalDlg();
            dlg.show('loadUserCarResult', '读取客户车辆结果', '未找到客户资料信息');
            return;
        }

        this.userCarPage.refresh(0, global.vehicle_url + 'list/' + user.ciid);
    },

    afterLoadUserCar: function(result, page, limit) {
        var user = userController.userModel.selUser;

        if (result.succeed) {
            userController.userView.showUserCar(page, limit, result.object, user.name);
        } else {
            userController.userView.showUserCar(page, limit, [], user.name);
        }
    },

    onAddUser: function () {
        var userEdit = {editType: 1};
        global.setParameter('userEdit', userEdit);
        window.location.href = './user_add.html';
    },

    onEditUser: function (userId) {
        var user = this.userModel.getUser(userId);

        if (user == undefined) {
            var dlg = new ModalDlg();
            dlg.show('editUserResult', '编辑用户结果', '未找到用户资料信息');
            return;
        }

        user.editType = 2;
        global.setParameter('userEdit', user);
        window.location.href = './user_add.html';
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                userController = new UserController();
            });
        }
    }
});
