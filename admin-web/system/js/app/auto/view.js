var AutoView = Class.extend({
    init: function () {
        $('#brandTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            //bAutoWidth: false,
            columns: [
                {data: 'index'},
                {render: function (data, type, full, meta) {
                    return '<img src="' + full.image + '" height="35">';
                }},
                {data: 'title'},
                {data: 'id'},
                {data: 'country'},
                {data: 'letter'},
                {width: '12%', render: function (data, type, full, meta) {
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑品牌"' +
                        ' onclick="resourceFindController.onClickEditResource(' + full.id + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示厂商"' +
                        ' onclick="autoController.onClickManu(' + full.id + ')">' +
                        '<i class="icon-arrow-right"></i></button>';
                }}
            ],
        });

        $('#manuTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            columns: [
                {data: 'index'},
                {data: 'title'},
                {data: 'id'},
                {width: '20%', render: function (data, type, full, meta) {
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑品牌"' +
                        ' onclick="resourceFindController.onClickEditResource(' + full.id + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示车系"' +
                        ' onclick="autoController.onClickCarset(' + full.id + ')">' +
                        '<i class="icon-arrow-right"></i></button>';
                }}
            ],
        });

        $('#carsetTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            columns: [
                {data: 'index'},
                {data: 'title'},
                {data: 'id'},
                {width: '20%', render: function (data, type, full, meta) {
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑车系"' +
                        ' onclick="autoController.onClickEditCarset(' + full.id + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示车款"' +
                        ' onclick="autoController.onClickCar(' + full.id + ')">' +
                        '<i class="icon-arrow-right"></i></button>';
                }}
            ],
        });
    },

    showBrand: function (data, page, limit) {
        var table = $('#brandTable').DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = (page - 1) * limit + i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    },

    showManu: function(data, brand) {
        $('#manuHeader').html('品牌厂商: ' + brand);
        var table = $('#manuTable').DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    },

    showCarset: function(data, manu) {
        $('#carsetHeader').html('厂商车系: ' + manu);
        var table = $('#carsetTable').DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    }
});