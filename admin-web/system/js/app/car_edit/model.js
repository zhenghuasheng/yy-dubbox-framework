var CarEditModel = Class.extend({
    editType: 1,
    car: null,
    carParam: null,

    init: function () {
        var data = global.getParameter('car');

        if (data != undefined) {
            this.editType = data.editType;
        }

        //添加
        if (!this.isAddMode()) {
            this.car = data;
        }
    },

    loadCarParam: function(callback) {
        $.ajax({
            type: 'get',
            url: global.auto_url + 'car/' + this.car.vid,
            model: this,
            contentType: 'application/json',

            success: function (result) {
                this.model.carParam = result.object;
                callback(result);
            }
        });
    },

    save: function (data, callback) {
        if (this.isAddMode()) {
            $.ajax({
                type: 'post',
                url: global.user_url + 'detail',
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
                url: global.auto_url + 'car',
                data: JSON.stringify(data),
                model: this,
                contentType: 'application/json',

                success: function (result) {
                    callback(result);
                }
            });
        }
    },

    setUser: function (id, user) {
        this.data[id] = user;
    },

    getUser: function (id) {
        return this.data[id];
    },

    isAddMode: function () {
        return (this.editType == 1);
    }
});
