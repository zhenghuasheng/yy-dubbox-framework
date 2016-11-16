var AutoModel = Class.extend({
    brand: null,
    manu: null,
    carset: null,
    selBrand: null,
    selManu: null,
    selCarset: null,

    init: function () {
    },

    cacheBrand: function (data) {
        this.brand = [];

        if (data == undefined) {
            return;
        }

        for (var i = 0; i < data.length; ++i) {
            var brand = data[i];
            this.brand[brand.id] = brand;
        }
    },

    loadManu: function(brandId, callback) {
        $.ajax({
            type: 'get',
            url: global.auto_url + 'brand/' + brandId + '/manu',
            model: this,
            contentType: 'application/json',

            success: function (result) {
                this.model.manu = [];

                if (result.succeed) {
                    for (var i = 0; i < result.object.length; ++i) {
                        var manu = result.object[i];
                        this.model.manu[manu.id] = manu;
                    }

                    callback(result);
                } else {
                    var dlg = new ModalDlg();
                    dlg.show('getManuResult', '车型厂商资源获取失败', '失败原因：' + result.description);
                }
            }
        });
    },

    loadCarset: function(manuId, callback) {
        var url = global.auto_url + 'manu/' + manuId + '/carset';

        if (global.system != '1') {
            url += '/system/' + global.system;
        }

        $.ajax({
            type: 'get',
            url: url,
            model: this,
            contentType: 'application/json',

            success: function (result) {
                this.model.carset = [];

                if (result.succeed) {
                    for (var i = 0; i < result.object.length; ++i) {
                        var carset = result.object[i];
                        this.model.carset[carset.id] = carset;
                    }

                    callback(result);
                } else {
                    var dlg = new ModalDlg();
                    dlg.show('getCarsetResult', '厂商车系资源获取失败', '失败原因：' + result.description);
                }
            }
        });
    },

    setSelBrand: function(brandId) {
        this.selBrand = this.brand[brandId];
        return this.selBrand;
    },

    setSelManu: function(manuId) {
        this.selManu = this.manu[manuId];
        return this.selManu;
    },

    setSelCarset: function(carsetId) {
        this.selCarset = this.carset[carsetId];
        return this.selCarset;
    },

    exportCar: function() {
        var url = global.auto_url + 'export';

        $.ajax({
            type: 'get',
            url: url,
            model: this,
            contentType: 'application/json',

            success: function (result) {
                if (result.succeed) {
                } else {
                    var dlg = new ModalDlg();
                    dlg.show('导出结果', '导出文件失败', '失败原因：' + result.description);
                }
            }
        });
    },
});
