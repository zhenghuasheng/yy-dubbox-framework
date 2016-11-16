/**
 * Created by wunan on 15-12-4.
 */
var LoginHelper = Class.extend({
    init: function () {
    },

    login: function (user, password, system, verify, url, callback) {
        function afterPrelogin(result) {
            if (!result.succeed) {
                callback(result);
                return;
            }

            var logonKey = result.object;
            var pwdMd5 = $.md5($.md5(password + logonKey.salt) + logonKey.extraKey);

            $.ajax({
                type: 'get',
                url: url + 'login/' + user + "/" + pwdMd5 + "/" + verify + "/" + system,
                model: this,

                success: function (result) {
                    callback(result);
                }
            })
        }

        $.ajax({
            type: 'get',
            url: url + 'prelogin/' + user + "/" + system,
            model: this,

            success: function (result) {
                afterPrelogin(result);
            }
        })
    }
});