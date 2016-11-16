var AutoController = Class.extend({
    autoView: null,
    autoModel: null,
    brandPage: null,

    init: function () {
        global.initMenu();
        this.autoView = new AutoView();
        this.autoModel = new AutoModel();
        this.brandPage = new PageData({
            url: global.auto_url + 'brand',
            callback: this.afterLoadBrand,
            prePageId: 'preBrand',
            nextPageId: 'nextBrand',
            curPageId: 'curBrand'
        });

        this.brandPage.refresh();
        this.initEvent();
    },

    initEvent: function () {
        $('#addCarsetBtn').click(function() {
            autoController.onClickAddCarset();
        });

        $('#exportBtn').click(function() {
            autoController.autoModel.exportCar();
        });

        $('#search').keydown(function(event) {
            if (event.keyCode == 13) {
                autoController.brandPage.option.condition = $('#search').val();
                autoController.brandPage.refresh(0);
            }
        });
    },

    afterLoadBrand: function(result, page, limit) {
        autoController.autoModel.cacheBrand(result.object);

        if (!result.succeed) {
            return;
        }

        autoController.autoView.showBrand(result.object, page, limit);
    },

    onClickManu: function(id) {
        var selBrand = this.autoModel.setSelBrand(id);

        this.autoModel.loadManu(id, function(result) {
            if (result.succeed) {
                autoController.autoView.showManu(result.object, selBrand.title);
            }
        })
    },

    onClickCarset: function(manuId) {
        var selManu = this.autoModel.setSelManu(manuId);

        this.autoModel.loadCarset(manuId, function(result) {
            if (result.succeed) {
                autoController.autoView.showCarset(result.object, selManu.title);
            }
        })
    },

    onClickCar: function(carsetId) {
        var selCarset = this.autoModel.setSelCarset(carsetId);
        global.setParameter('carset', selCarset);
        window.location.href = './car.html';
    },

    onClickEditCarset: function(carsetId) {
        var selCarset = this.autoModel.setSelCarset(carsetId);
        selCarset.editType = 2;
        global.setParameter('carsetEdit', selCarset);
        window.location.href = './carset_edit.html';
    },

    onClickAddCarset: function() {
        global.setParameter('carsetEdit', {editType : 1});
        window.location.href = './carset_edit.html';
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                autoController = new AutoController();
            });
        }
    }
});
