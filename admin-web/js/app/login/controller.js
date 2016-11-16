var LoginController = Class.extend({
    loginView: null,
    loginModel: null,

    init: function () {
        this.loginSequence = this.uuid();
        this.loginView = new LoginView();
        this.loginModel = new LoginModel();
        this.loginView.setUser(this.loginModel.data);
        this.initEvent();
        this.show();
    },

    initEvent: function () {
        $('#loginBtn').click(this.onBtnClick);
        $('#remeber').click(function() {
            loginController.loginModel.data.remeber = this.checked;
        });

        $('#captchaImage').click(function () {
            loginController.show();
        });
    },

    show: function () {
        $('#captchaImage').attr('src', global.captcha_url
            + 'account/' + global.system + '/' + this.loginSequence + "?"
            + Math.floor(Math.random() * 0x1000000), 1);
    },

    onBtnClick: function () {
        //loginController.createToken();
        //return;
        var userInfo = loginController.loginView.getLoginParam();

        if (loginController.loginModel.isRemeber()) {
            global.setLocalParam('remeber', {remeber : true, user : userInfo.phone});
        } else {
            global.delLocalParam('remeber');
        }

        var login = new LoginHelper();
        login.login(userInfo.phone, userInfo.password
            , global.system, null, global.user_url, afterLogin);

        function afterLogin(result) {
            if (!result.succeed) {
                var dlg = new ModalDlg();
                dlg.show('loginResult', "出错喽", "登录失败:" + result.description);
                return;
            }

            global.setParameter("userInfo", result.object);
            window.location.href = "./index.html";
        }
    },

    createToken: function() {
        var tokenParam = {
            issuer: 'admin_1',
            userId: 9,
            system: 1,
            expireTime: 518400
        };

        $.ajax({
            type: 'post',
            url: global.auth_url + "token",
            model: this,
            data: JSON.stringify(tokenParam),
            contentType: 'application/json',

            success: function (result) {

            }
        })
    },

    uuid: function () {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }

        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";

        return s.join("");
    }
});

define(function () {
    return {
        setup: function () {
            requirejs(['../../jquery.md5', 'utility/login', 'utility/modal'], function () {
                loginController = new LoginController();
            });
        }
    }
});
