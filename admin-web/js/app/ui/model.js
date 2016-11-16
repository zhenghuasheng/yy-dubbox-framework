var LoginModel = Class.extend({
    data: null,

    init: function () {
    },

    preLogin: function (userInfo, afterPrelogin) {
        $.ajax({
            type: 'get',
            url: global.user_url + 'prelogin/' + userInfo.phone + "/1001",
            model: this,

            success: function (result) {
                this.model.data = result.object;
                afterPrelogin(result);
            }
        })
    },

    login: function (data, callback) {
        $.ajax({
            type: 'get',
            url: global.user_url + 'login/' + data.name + "/" + data.password + "/" + data.verify + "/" + data.system,
            model: this,

            success: function (result) {
                this.model.data = result;
                callback(result);
            }
        })
    },
});
