var IndexController = Class.extend({
    indexView: null,
    indexModel: null,

    init: function () {
        global.initMenu();
        this.indexView = new IndexView();
        this.indexModel = new IndexModel();
        this.initEvent();
    },

    initEvent: function () {
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal'], function () {
                indexController = new IndexController();
            });
        }
    }
});
