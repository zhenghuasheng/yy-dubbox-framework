var CarsetEditController = Class.extend({
    carsetEditView: null,
    carsetEditModel: null,

    init: function () {
        global.initMenu();
        this.initEvent();
        this.carsetEditView = new CarsetEditView();
        this.carsetEditModel = new CarsetEditModel();

        if (!this.carsetEditModel.isAddMode()) {
            this.carsetEditView.setFormData(this.carsetEditModel.carset);
        }
    },

    initEvent: function () {
        function onSaveClick() {
            var data = carsetEditController.carsetEditView.getFormData(
                carsetEditController.carsetEditModel.role);
            carsetEditController.carsetEditModel.save(data, carsetEditController.afterSave);
        }

        function onFinishClick() {
            window.location.href = 'auto.html';
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
            if (carsetEditController.carsetEditModel.isAddMode()) {
                carsetEditController.carsetEditView.resetFormData();
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
                carsetEditController = new CarsetEditController();
            });
        }
    }
});
