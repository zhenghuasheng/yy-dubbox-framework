var RoleAddView = Class.extend({
    init: function () {
    },

    getFormData: function (old) {
        var data = {};

        if (old != undefined) {
            data = old;
        }

        data.name = $('#name').val();
        data.remark = $('#remark').val();
        return data;
    },

    setFormData: function (data) {
        $('#name').val(data.name);
        $('#remark').val(data.remark);
    },

    resetFormData: function () {
        $("#name").val('');
        $("#remark").val('');
    }
});