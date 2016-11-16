var UserAddController = Class.extend({
    userAddView: null,
    userAddModel: null,
    userPage: null,

    init: function () {
        global.initMenu();
        this.initEvent();
        this.userAddView = new UserAddView();
        this.userAddModel = new UserAddModel();

        if (!this.userAddModel.isAddMode()) {
            this.userAddView.setFormData(this.userAddModel.user);
        }
    },

    initEvent: function () {
        function onSaveClick() {
            var data = userAddController.userAddView.getFormData(
                userAddController.userAddModel.user);

            if (data.password.length > 0) {
                data.password = $.md5(data.password + data.salt);
            }

            if (userAddController.userAddModel.isAddMode()) {
                data.system = global.system;
            }

            userAddController.userAddModel.save(data, userAddController.afterSave);
        }

        function onFinishClick() {
            window.location.href = 'user.html';
        }

        $('#save').click(onSaveClick);
        $('#finish').click(onFinishClick);
    },

    afterSave: function (result) {
        var dlg = new ModalDlg();

        if (result.succeed) {
            dlg.show('saveResult', '保存结果', '保存成功', afterShow);
        } else {
            dlg.show('saveResult', '保存结果', '保存失败：' + result.description);
        }

        function afterShow() {
            if (userAddController.userAddModel.isAddMode()) {
                userAddController.userAddView.resetFormData();
            } else {
                window.location.href = 'user.html';
            }
        }
    },
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata', '../../../../js/jquery.md5'], function () {
                userAddController = new UserAddController();
            });
        }
    }
});
