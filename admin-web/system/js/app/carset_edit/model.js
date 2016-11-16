var CarsetEditModel = Class.extend({
    carset: null,

    init: function () {
        this.carset = global.getParameter('carsetEdit');
    },

    save: function (data, callback) {
        data.stid = global.system;

        //if (this.isAddMode()) {
        //    $.ajax({
        //        type: 'post',
        //        url: global.auth_url + 'role',
        //        data: JSON.stringify(data),
        //        model: this,
        //        contentType: 'application/json',
        //
        //        success: function (result) {
        //            callback(result);
        //        }
        //    });
        //} else {
        //    $.ajax({
        //        type: 'put',
        //        url: global.auth_url + 'role',
        //        data: JSON.stringify(data),
        //        model: this,
        //        contentType: 'application/json',
        //
        //        success: function (result) {
        //            callback(result);
        //        }
        //    });
        //}
    },

    isAddMode: function () {
        return (this.carset.editType == 1);
    }
});
