var RoleAddModel = Class.extend({
    role: null,

    init: function () {
        this.role = global.getParameter('roleInfo');
    },

    save: function (data, callback) {
        data.stid = global.system;

        if (this.isAddMode()) {
            $.ajax({
                type: 'post',
                url: global.auth_url + 'role',
                data: JSON.stringify(data),
                model: this,
                contentType: 'application/json',

                success: function (result) {
                    callback(result);
                }
            });
        } else {
            $.ajax({
                type: 'put',
                url: global.auth_url + 'role',
                data: JSON.stringify(data),
                model: this,
                contentType: 'application/json',

                success: function (result) {
                    callback(result);
                }
            });
        }
    },

    isAddMode: function () {
        return (this.role.editType == 1);
    }
});
