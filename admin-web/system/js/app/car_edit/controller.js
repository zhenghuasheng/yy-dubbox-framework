var CarEditController = Class.extend({
    carEditView: null,
    carEditModel: null,
    userPage: null,

    init: function () {
        global.initMenu();
        this.initEvent();
        this.carEditView = new CarEditView();
        this.carEditModel = new CarEditModel();

        if (!this.carEditModel.isAddMode()) {
            this.carEditModel.loadCarParam(function (result) {
                carEditController.afterLoadParam(result);
            });
        }
    },

    initEvent: function () {
        function onSaveClick() {
            var data = carEditController.carEditView.getFormData(
                carEditController.carEditModel.car);

            if (carEditController.carEditModel.isAddMode()) {
                data.system = global.system;
            } else {

            }

            carEditController.carEditModel.save(data, carEditController.afterSave);
        }

        function onFinishClick() {
            window.location.href = 'user.html';
        }

        $('#save').click(onSaveClick);
        $('#finish').click(onFinishClick);

        $('#fileupload').fileupload({
            url: global.upload_url + "?dir=car",
            dataType: 'json',
            start: function (e) {
                this.token = $.ajaxSetup().headers['auth-token'];
                delete $.ajaxSetup().headers['auth-token'];
            },

            stop: function (e) {
                $.ajaxSetup().headers['auth-token'] = this.token;
            },

            done: function (e, data) {
                $.each(data.result.files, function (index, file) {
                    $('#image').val(file.url);
                    $('#image_url').attr('src', file.url);
                });
            },
            //progressall: function (e, data) {
            //    var progress = parseInt(data.loaded / data.total * 100, 10);
            //    $('#progress .progress-bar').css(
            //        'width',
            //        progress + '%'
            //    );
            //}
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    },

    afterLoadParam: function(result) {
        if (result.succeed) {
            this.carEditView.setFormData(this.carEditModel.car, this.carEditModel.carParam);
        } else {
            var dlg = new ModalDlg();
            dlg.show('loadParamResult', '读取车款参数', '读取车款参数失败:' + result.description);
        }
    },

    afterSave: function (result) {
        var dlg = new ModalDlg();

        if (result.succeed) {
            dlg.show('saveResult', '保存结果', '保存成功', afterShow);
        } else {
            dlg.show('saveResult', '保存结果', '保存失败：' + result.description);
        }

        function afterShow() {
            if (carEditController.carEditModel.isAddMode()) {
                carEditController.carEditView.resetFormData();
            } else {
                window.location.href = 'car.html';
            }
        }
    },
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata', '../../../../js/jquery.md5'], function () {
                carEditController = new CarEditController();
            });
        }
    }
});
