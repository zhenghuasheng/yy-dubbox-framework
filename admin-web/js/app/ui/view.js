var LoginView = Class.extend({
    init: function () {
    },

    showResult: function (data) {
        var extraKey = data.data.extraKey;
        var salt = data.data.salt;
        sessionStorage.setItem("extraKey", extraKey);
        sessionStorage.setItem("salt", salt);
    },

    getLoginParam: function () {
        return {
            phone: $("#inputPhone").val(),
            password: $("#inputPassword").val()
        };
    },

    //getLoginParam: function () {
    //    var salt = sessionStorage.getItem("salt");
    //    var extrakey = sessionStorage.getItem("extraKey");
    //    var psw = $.md5($("#password").val() + salt);
    //    var passwordLogin = $.md5(psw + extrakey);
    //    var data = {
    //        phone: $("#tel").val(),
    //        pwd: passwordLogin
    //    };
    //    return data;
    //}

});