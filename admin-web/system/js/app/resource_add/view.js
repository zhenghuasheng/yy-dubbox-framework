var ResourceAddView = Class.extend({
    table: null,
    init: function () {
        this.table = $('#resourceTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            autoWidth: false,
            columns: [
                {data: 'index'},
                {data: 'name'},
                {width: '12%', render: function (data, type, full, meta) {
                    resourceAddController.resourceAddModel.setResource(full.id, full);
                    return '<button class="btn btn-xs btn-success"' +
                        ' onclick="resourceAddController.onClickSelResource(' + full.id + ', this)">' +
                        '<i class="icon-check"></i></button>';
                }}
            ],
        });
    },

    getFormData: function (old) {
        var data = {};

        if (old != undefined) {
            data = old;
        }

        data.name = $('#rname').val();
        data.rspath = $('#rspath').val();
        data.remark = $('#remark').val();
        data.type = $('#type').val();
        data.stid = $('#stid').val();
        data.vieworder = $('#order').val();
        data.icon = $('#icon').val();
        data.url = $('#url').val();
        return data;
    },

    setFormData: function (data) {
        $('#rname').val(data.name);
        $('#rspath').val(data.rspath);
        $('#remark').val(data.remark);
        $('#type').val(data.type);
        $('#stid').val(data.stid);
        $('#order').val(data.vieworder);
        $('#icon').val(data.icon);
        $('#url').val(data.url);
        $('#pitemid').val(data.pitemid);
    },

    setParent: function (parent) {
        if (parent != undefined) {
            $('#pitemid').val(parent);
        }
    },

    resetFormData: function () {
        $("#rname").val('');
        $("#rspath").val('');
        $("#remark").val('');
        $("#order").val('');
        $("#icon").val('');
        $("#url").val('');
        $('#pitemid').val('');
    },

    showResource: function (page, limit, data) {
        var table = $('#resourceTable').DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = (page - 1) * limit + i + 1;
            table.row.add(data[i]);
        }


        table.draw();
    },

    setSelResource: function (data) {
        if (data == undefined) {
            $('#selResource').html('当前选择：');
        } else {
            $('#selResource').html('当前选择：' + data.name);
        }
    }
});