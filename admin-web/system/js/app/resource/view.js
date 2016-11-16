var ResourceFindView = Class.extend({
    init: function () {
        $('#resourceTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            columns: [
                {data: 'index'},
                {data: 'name'},
                {data: 'rspath'},
                {data: 'id'},
                {data: 'itemid'},
                {data: 'pitemid'},
                {width: '12%', render: function (data, type, full, meta) {
                    resourceFindController.resourceFindModel.setResource(full.id, full);
                    return '<button class="btn btn-xs btn-success" data-toggle="tooltip" title="添加子资源"' +
                        ' onclick="resourceFindController.onClickAddResource(' + full.id + ')">' +
                        '<i class="icon-plus"></i></button>' +
                        '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑资源"' +
                        ' onclick="resourceFindController.onClickEditResource(' + full.id + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-danger" data-toggle="tooltip" title="移除资源"' +
                        ' onclick="resourceFindController.onClickDelResource(' + full.id + ')">' +
                        '<i class="icon-remove"></i></button>';
                }},
            ],
        });
    },

    showResult: function (page, limit, data) {
        var table = $('#resourceTable').DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = (page - 1) * limit + i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    }
});