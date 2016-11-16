var ResourceFindModel = Class.extend({
    data: [],

    init: function () {
    },

    setResource: function (id, resource) {
        this.data[id] = resource;
    },

    getResource: function (id) {
        return this.data[id];
    },

    delResource: function (id, callback) {
        var data = this.data[id];

        if (data == undefined) {
            callback({succeed: false, description: '没有找到要移除的资源'});
            return;
        }

        var param = {id: data.id, delete: 1};

        $.ajax({
            type: 'put',
            url: global.auth_url + 'resource',
            data: JSON.stringify(param),
            model: this,
            contentType: 'application/json',

            success: function (result) {
                callback(result);
            }
        });
    }
});
