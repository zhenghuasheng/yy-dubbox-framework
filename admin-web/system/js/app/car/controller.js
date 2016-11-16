var CarController = Class.extend({
    carView: null,
    carModel: null,
    carPage: null,

    init: function () {
        global.initMenu();
        this.carView = new CarView();
        this.carModel = new CarModel();
        this.carView.setCarPanelTitle(this.carModel.carset);

        this.carPage = new PageData({
            url: global.auto_url + 'carset/' + this.carModel.carset.id + '/car',
            callback: this.afterLoadCar,
            prePageId: 'preCar',
            nextPageId: 'nextCar',
            curPageId: 'curCar'
        });

        this.carPage.refresh();
        this.initEvent();
    },

    initEvent: function () {
        $('#addCarBtn').click(function(){
            carController.onClickAdd();
        });

        $('#search').keydown(function(event) {
            if (event.keyCode == 13) {
                carController.carPage.option.condition = $('#search').val();
                carController.carPage.refresh(0);
            }
        });
    },

    afterLoadCar: function(result, page, limit) {
        carController.carModel.cacheCar(result.object);

        if (!result.succeed) {
            return;
        }

        carController.carView.showCar(result.object, page, limit);
    },

    onClickParam: function(id) {
        var car = this.carModel.getCacheCar(id);
        car.editType = 2;
        global.setParameter('car', car);
        window.location.href = './car_edit.html';
    },

    onClickAdd: function() {
        global.setParameter('car', {editType : 1});
        window.location.href = './car_edit.html';
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal', 'utility/pagedata'], function () {
                carController = new CarController();
            });
        }
    }
});
