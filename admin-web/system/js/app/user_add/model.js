var UserAddModel = Class.extend({
    data: [],//缓存资源项
    editType: 1,
    user: null,//当前资源内容

    init: function () {
        var data = global.getParameter('userEdit');

        if (data != undefined) {
            this.editType = data.editType;
        }

        //添加
        if (!this.isAddMode()) {
            this.user = data;
        }
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
                url: global.user_url + 'detail',
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
