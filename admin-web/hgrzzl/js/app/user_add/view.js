var UserAddView = Class.extend({
    init: function () {
    },

    getFormData: function (old) {
        var data = {};

        if (old != undefined) {
            data = old;
        }

        data.loginid = $('#loginid').val();
        data.username = $('#username').val();
        data.email = $('#email').val();
        data.password = $('#password').val();
        data.salt = $('#salt').val();
        data.name = $('#name').val();
        data.phone = $('#phone').val();
        data.qq = $('#qq').val();
        data.weixin = $('#weixin').val();
        data.idcard = $('#idcard').val();
        data.address = $('#address').val();
        data.residence = $('#residence').val();
        data.education = $('#education').val();
        data.technicaltitle = $('#technicaltitle').val();
        data.company = $('#company').val();
        data.unitaddress = $('#companyaddress').val();
        data.unitproperties = $('#comtype').val();
        data.workinglife = $('#workyear').val();
        data.desc = $('#remark').val();
        data.sex = $('input[name="sexRadios"]:checked').val();
        data.marriage = $('input[name="marryRadios"]:checked').val();
        return data;
    },

    setFormData: function (data) {
        $('#loginid').val(data.loginid);
        $('#username').val(data.username);
        $('#email').val(data.email);
        $('#salt').val(data.salt);
        $('#password').val('');

        $('#name').val(data.name);
        $('#phone').val(data.phone);
        $('#qq').val(data.qq);
        $('#weixin').val(data.weixin);
        $('#idcard').val(data.idcard);
        $('#address').val(data.address);
        $('#residence').val(data.residence);
        $('#education').val(data.education);
        $('#technicaltitle').val(data.technicaltitle);
        $('#company').val(data.company);
        $('#companyaddress').val(data.unitaddress);
        $('#comtype').val(data.unitproperties);
        $('#workyear').val(data.workinglife);
        $('#remark').val(data.desc);

        if (data.sex != undefined) {
            $('input[name="sexRadios"][value="' + data.sex + '"]').attr('checked', 'checked');
        }

        if (data.marriage != undefined) {
            $('input[name="marryRadios"][value="' + data.marriage + '"]').attr('checked', 'checked');
        }
    },

    resetFormData: function () {
        this.setFormData({});
    },

});