var ResourceAddController = Class.extend({
    resourceAddView: null,
    resourceAddModel: null,
    resourcePage: null,

    init: function () {
        global.initMenu();
        this.initEvent();
        this.resourceAddView = new ResourceAddView();
        this.resourceAddModel = new ResourceAddModel();
        this.resourceAddView.setParent(this.resourceAddModel.getParent());

        if (!this.resourceAddModel.isAddMode()) {
            this.resourceAddView.setFormData(this.resourceAddModel.resource);
        }

        this.resourcePage = new PageData({
            url: global.auth_url + 'resource',
            callback: this.showResource,
            prePageId: 'preResource',
            nextPageId: 'nextResource',
            curPageId: 'currentResource'
        });

        this.resourcePage.refresh();
    },

    initEvent: function () {
        function onSaveClick() {
            var data = resourceAddController.resourceAddView.getFormData(
                resourceAddController.resourceAddModel.resource);

            if (resourceAddController.resourceAddModel.selResource != undefined) {
                data.pitemid = resourceAddController.resourceAddModel.selResource.itemid;
            } else if (resourceAddController.resourceAddModel.parent != undefined) {
                data.pitemid = resourceAddController.resourceAddModel.parent.itemid;
            }

            resourceAddController.resourceAddModel.save(data, resourceAddController.afterSave);
        }

        function onFinishClick() {
            window.location.href = 'resource.html';
        }

        $('#save').click(onSaveClick);
        $('#finish').click(onFinishClick);
        $('#selResource').click(function () {
            resourceAddController.resourceAddModel.setSelResource(undefined);
            resourceAddController.resourceAddView.setSelResource(undefined);
        });

        $('#removeParent').click(function () {
            if (resourceAddController.resourceAddModel.isAddMode()) {
                resourceAddController.resourceAddModel.parent = undefined;
            } else {
                resourceAddController.resourceAddModel.resource.pitemid = 0;
            }

            resourceAddController.resourceAddView.setParent('0');
        });
    },

    showResource: function (result, page, limit) {
        if (result.succeed) {
            resourceAddController.resourceAddView.showResource(page, limit, result.object);
        }
    },

    afterSave: function (result) {
        var dlg = new ModalDlg();

        if (result.succeed) {
            dlg.show('saveResult', '保存结果', '保存成功', afterShow);
            global.delParameter('authInfo');
        } else {
            dlg.show('saveResult', '保存结果', '保存失败：' + result.description);
        }

        function afterShow() {
            if (resourceAddController.resourceAddModel.isAddMode()) {
                resourceAddController.resourceAddView.resetFormData();
            } else {
                window.location.href = 'resource.html';
            }
        }
    },

    onClickSelResource: function (itemid, row) {
        var sel = resourceAddController.resourceAddModel.getResource(itemid);

        if (sel == undefined) {
            return;
        }

        resourceAddController.resourceAddModel.setSelResource(sel);
        resourceAddController.resourceAddView.setSelResource(sel);
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                resourceAddController = new ResourceAddController();
            });
        }
    }
});
