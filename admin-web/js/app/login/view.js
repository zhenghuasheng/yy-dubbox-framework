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

    setUser: function(remeberUser) {
        if (remeberUser == undefined) {
            return;
        }

        $('#remeber').attr('checked', remeberUser.remeber);
        $('#inputPhone').val(remeberUser.user);
    }
});