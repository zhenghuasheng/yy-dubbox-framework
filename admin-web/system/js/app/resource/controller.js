var ResourceFindController = Class.extend({
    resourceFindView: null,
    resourceFindModel: null,
    resourcePage: null,

    init: function () {
        global.initMenu();
        this.initEvent();
        this.resourceFindView = new ResourceFindView();
        this.resourceFindModel = new ResourceFindModel();
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
    },

    showResource: function (result, page, limit) {
        if (result.succeed) {
            resourceFindController.resourceFindView.showResult(page, limit, result.object);
        }
    },

    onClickAddResource: function (id) {
        var resource = resourceFindController.resourceFindModel.getResource(id);

        if (resource == undefined) {
            resource = {};
        }

        resource.editType = 1;
        global.setParameter('resourceInfo', resource);
        window.location.href = "resource_add.html";
    },

    onClickEditResource: function (id) {
        var resource = resourceFindController.resourceFindModel.getResource(id);
        resource.editType = 2;
        global.setParameter('resourceInfo', resource);
        window.location.href = "resource_add.html";
    },

    onClickDelResource: function (id) {
        function afterDelete(result) {
            var dlg = new ModalDlg();

            if (result.succeed) {
                dlg.show('delResult', '删除结果', '资源已经移除到回收站中', afterDlgClose);
            } else {
                dlg.show('delResult', '删除结果', '资源移除失败：' + result.description);
            }
        }

        function afterDlgClose() {
            resourceFindController.resourcePage.refresh();
        }

        resourceFindController.resourceFindModel.delResource(id, afterDelete);
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                resourceFindController = new ResourceFindController();
            });
        }
    }
});
