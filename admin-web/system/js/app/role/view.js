var RoleView = Class.extend({
    init: function () {
        this.initRoleTable();
        this.initUserRoleTable();
        this.initUserTable();
        this.initAuthTable();
    },

    initRoleTable: function () {
        $('#roleTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            columns: [
                {data: 'index'},
                {data: 'name'},
                {data: 'remark'},
                {data: 'rlid'},
                {width: '20%', render: function (data, type, full, meta) {
                    roleController.roleModel.setRole(full.rlid, full);
                    return '<button class="btn btn-xs btn-warning" data-toggle="tooltip" title="编辑角色"' +
                        ' onclick="roleController.onClickEditRole(' + full.rlid + ')">' +
                        '<i class="icon-pencil"></i></button>' +
                        '<button class="btn btn-xs btn-danger" data-toggle="tooltip" title="删除角色"' +
                        ' onclick="roleController.onClickDelRole(' + full.rlid + ')">' +
                        '<i class="icon-remove"></i></button>' +
                        '<button class="btn btn-xs btn-primary" data-toggle="tooltip" title="配置角色权限"' +
                        ' onclick="roleController.onClickShowAuthDlg(' + full.rlid + ')">' +
                        '<i class="icon-leaf"></i></button>' +
                        '<button class="btn btn-xs btn-info" data-toggle="tooltip" title="显示角色成员"' +
                        ' onclick="roleController.onClickRoleUser(' + full.rlid + ')">' +
                        '<i class="icon-group"></i></button>';
                }}
            ],
        });
    },

    initUserRoleTable: function () {
        $('#userRoleTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            columns: [
                {data: 'index'},
                {data: 'name'},
                {data: 'userId'},
                {width: '12%', render: function (data, type, full, meta) {
                    //roleController.roleModel.setRole(full.rlid, full);
                    return '<button class="btn btn-xs btn-danger" data-toggle="tooltip" title="移除用户"' +
                        ' onclick="roleController.onClickRemoveUser(' + full.id + ')">' +
                        '<i class="icon-remove"></i></button>';
                }}
            ],
        });
    },

    initUserTable: function() {
        $('#userTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            bAutoWidth: false,
            columns: [
                {data: 'index'},
                {data: 'f_name'},
                {data: 'f_mid'},
                {data: 'f_phone'},
                {render: function (data, type, full, meta) {
                    roleController.roleModel.addUser(full);
                    var btnStyle = 'btn-info';

                    if (roleController.roleModel.getSeluser(full.f_mid) != undefined) {
                        btnStyle = 'btn-danger';
                    }

                    return '<button class="btn btn-xs ' + btnStyle + '" data-toggle="tooltip"' +
                        ' title="添加" onclick="roleController.onClickSelUser(' + full.f_mid + ', this)">' +
                        '<i class="icon-check"></i></button>';
                }}
            ],
        });
    },

    initAuthTable: function() {
        $('#authTable').DataTable({
            paging: false,
            searching: false,
            info: false,
            bAutoWidth: false,
            columns: [
                {data: 'index'},
                {data: 'name'},
                {data: 'rspath'},
                {render: function (data, type, full, meta) {
                    roleController.roleModel.resourceCache[full.id] = full;
                    var btnStyle = 'btn-info';
                    var roleResource = roleController.roleModel.getRoleResource([full.id]);

                    if (roleResource != undefined) {
                        if (roleResource.available) {
                            btnStyle = 'btn-danger';
                        } else {
                            btnStyle = 'btn-warning';
                        }
                    }

                    return '<button class="btn btn-xs ' + btnStyle + '" data-toggle="tooltip"' +
                        ' title="添加" onclick="roleController.onClickSelAuth(' + full.id + ', this)">' +
                        '<i class="icon-check"></i></button>';
                }}
            ],
        });
    },

    showRole: function (page, limit, data) {
        this.showTable($('#roleTable'), page, limit, data);
    },

    showUserRole: function (page, limit, data, role) {
        this.showTable($('#userRoleTable'), page, limit, data);
        $('#roleUserHeader').html('角色成员：' + role.name);
    },

    resetUseRole: function (title) {
        var table = $('#userRoleTable').DataTable();
        table.clear();
        table.draw();
        $('#roleUserHeader').html('角色成员：' + title);
    },

    resetAuthRole: function () {
        var table = $('#authTable').DataTable();
        table.clear();
        table.draw();
    },

    showUser: function (page, limit, data) {
        this.showTable($('#userTable'), page, limit, data);
    },

    showResource: function (page, limit ,data) {
        this.showTable($('#authTable'), page, limit, data);
    },

    showTable: function (tableObj, page, limit, data) {
        var table = tableObj.DataTable();
        table.clear();

        for (var i = 0; i < data.length; ++i) {
            data[i].index = (page - 1) * limit + i + 1;
            table.row.add(data[i]);
        }

        table.draw();
    }
});