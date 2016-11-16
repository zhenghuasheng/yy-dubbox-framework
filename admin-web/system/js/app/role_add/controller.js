var RoleAddController = Class.extend({
    roleAddView: null,
    roleAddModel: null,

    init: function () {
        global.initMenu();
        this.initEvent();
        this.roleAddView = new RoleAddView();
        this.roleAddModel = new RoleAddModel();

        if (!this.roleAddModel.isAddMode()) {
            this.roleAddView.setFormData(this.roleAddModel.role);
        }
    },

    initEvent: function () {
        function onSaveClick() {
            var data = roleAddController.roleAddView.getFormData(
                roleAddController.roleAddModel.role);
            roleAddController.roleAddModel.save(data, roleAddController.afterSave);
        }

        function onFinishClick() {
            window.location.href = 'role.html';
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
            if (roleAddController.roleAddModel.isAddMode()) {
                roleAddController.roleAddView.resetFormData();
            } else {
                window.location.href = 'role.html';
            }
        }
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                roleAddController = new RoleAddController();
            });
        }
    }
});
