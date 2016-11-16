var UserView = Class.extend({
    init: function () {
        this.initUserTable();
        this.initUserCarTable();
    },

    initUserTable: function () {
        $('#userTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            bAutoWidth: false,
            columns: [
                {data: 'index'},
                {data: 'loginid'},
                {data: 'username'},
                {data: 'mid'},
                {data: 'name'},
                {data: 'phone'},
                {data: 'idcard'},
                {data: 'sex'},
                {render: function (data, type, full, meta) {
                    userController.userModel.setUser(full);
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑用户"' +
                        ' onclick="userController.onEditUser(' + full.mid + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-danger" data-toggle="tooltip" title="删除用户"' +
                        ' onclick="userController.onClickDelUser(' + full.mid + ')">' +
                        '<i class="icon-remove"></i></button>' +
                        '<button class="btn btn-xs btn-primary" data-toggle="tooltip" title="配置权限"' +
                        ' onclick="userController.onClickShowAuthDlg(' + full.mid + ')">' +
                        '<i class="icon-leaf"></i></button>' +
                        '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示车辆信息"' +
                        ' onclick="userController.loadUserCar(' + full.mid + ')">' +
                        '<i class="icon-ambulance"></i></button>';
                }}
            ],
        });
    },

    initUserCarTable: function () {
        $('#userCarTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            columns: [
                {data: 'index'},
                {data: 'f_vid'},
                {data: 'f_plate_number'},
                {width: '25%', render: function (data, type, full, meta) {
                    //userController.userModel.setUser(full.rlid, full);
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑用户"' +
                        ' onclick="userController.onClickEditUser(' + full.mid + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-danger" data-toggle="tooltip" title="删除用户"' +
                        ' onclick="userController.onClickDelUser(' + full.mid + ')">' +
                        '<i class="icon-remove"></i></button>';
                }}
            ],
        });
    },

    showUser: function (page, limit, data) {
        this.showTable($('#userTable'), page, limit, data);
    },

    showTable: function (tableObj, page, limit, data) {
        var table = tableObj.DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = (page - 1) * limit + i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    },

    showUserCar: function(page, limit, data, name) {
        this.showTable($('#userCarTable'), page, limit, data);
        $('#userCarHeader').html('车辆列表: ' + name);
    }
});