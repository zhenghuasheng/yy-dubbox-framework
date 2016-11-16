var CarView = Class.extend({
    init: function () {
        $('#carTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            bAutoWidth: false,
            columns: [
                {data: 'index'},
                {render: function (data, type, full, meta) {
                    return '<img src="' + full.image + '" height="50">';
                }},
                {data: 'subject'},
                {data: 'vid'},
                {data: 'year'},
                {data: 'salestatus'},
                {data: 'prices'},
                {width: '12%', render: function (data, type, full, meta) {
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑详情"' +
                        ' onclick="carController.onClickParam(' + full.vid + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示图片"' +
                        ' onclick="carController.onClickManu(' + full.vid + ')">' +
                        '<i class="icon-picture"></i></button>';
                }}
            ],
        });

        //$('#manuTable').DataTable({
        //    paging: false,
        //    searching: false,
        //    info: false,
        //    columns: [
        //        {data: 'index'},
        //        {data: 'title'},
        //        {data: 'id'},
        //        {width: '20%', render: function (data, type, full, meta) {
        //            return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑品牌"' +
        //                ' onclick="resourceFindController.onClickEditResource(' + full.id + ')">' +
        //                '<i class="icon-pencil"></i></button>' +
        //                '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示车系"' +
        //                ' onclick="carController.onClickCarset(' + full.id + ')">' +
        //                '<i class="icon-arrow-right"></i></button>';
        //        }}
        //    ],
        //});
        //
        //$('#carsetTable').DataTable({
        //    paging: false,
        //    searching: false,
        //    info: false,
        //    columns: [
        //        {data: 'index'},
        //        {data: 'title'},
        //        {data: 'id'},
        //        {width: '20%', render: function (data, type, full, meta) {
        //            return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑品牌"' +
        //                ' onclick="resourceFindController.onClickEditResource(' + full.id + ')">' +
        //                '<i class="icon-pencil"></i></button>' +
        //                '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示车系"' +
        //                ' onclick="carController.onClickCarset(' + full.id + ')">' +
        //                '<i class="icon-arrow-right"></i></button>';
        //        }}
        //    ],
        //});
    },

    showCar: function (data, page, limit) {
        var table = $('#carTable').DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = (page - 1) * limit + i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    },

    setCarPanelTitle: function(carset) {
        $('#widget-car').html('车款管理 - ' + carset.fullName);
    }
});