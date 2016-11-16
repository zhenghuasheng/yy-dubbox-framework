var ResourceAddModel = Class.extend({
    parent: null,
    data: [],//缓存资源项
    selResource: null,
    editType: 1,
    resource: null,//当前资源内容

    init: function () {
        var data = global.getParameter('resourceInfo');

        if (data != undefined) {
            this.editType = data.editType;
        }

        //添加
        if (this.isAddMode()) {
            this.parent = data;
        } else {
            this.resource = data;
        }
    },

    getParent: function () {
        if (this.isAddMode()) {
            if (this.parent == undefined) {
                return undefined;
            }

            return this.parent.itemid;
        } else {
            return this.resource.pitemid;
        }
    },

    setSelResource: function (resource) {
        this.selResource = resource;
    },

    save: function (data, callback) {
        if (this.isAddMode()) {
            $.ajax({
                type: 'post',
                url: global.auth_url + 'resource',
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
                url: global.auth_url + 'resource',
                data: JSON.stringify(data),
                model: this,
                contentType: 'application/json',

                success: function (result) {
                    callback(result);
                }
            });
        }
    },

    setResource: function (id, resource) {
        this.data[id] = resource;
    },

    getResource: function (id) {
        return this.data[id];
    },

    isAddMode: function () {
        return (this.editType == 1);
    }
});
