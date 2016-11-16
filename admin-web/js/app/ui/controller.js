var LoginController = Class.extend({
    loginView: null,
    loginModel: null,

    init: function () {
        this.loginView = new LoginView();
        this.loginModel = new LoginModel();
        this.initEvent();
        this.show();

    },

    initEvent: function () {
    },

    show: function () {
        var dlg = new ModalDlg();
        dlg.show('test', 'hello', 'very good');
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['utility/modal'], function () {
                loginController = new LoginController();
            });
        }
    }
});
