var Global = Class.extend({
    server_url: 'http://58.20.41.200:8383',
    info_url: 'http://58.20.41.200:8384',
    //用户接口地址
    user_url: 'http://192.168.30.19:8480/services/users/',
    //用户资料接口地址
    cust_url: 'http://192.168.30.19:8480/services/cust/',
    //用户车辆接口地址
    vehicle_url: 'http://192.168.30.19:8480/services/vehicle/',
    //车型库接口地址
    auto_url: 'http://192.168.30.19:8391/services/auto/',
    //权限认证接口地址
    auth_url: 'http://192.168.30.19:8401/services/auth/',
    //验证服务接口地址
    captcha_url: 'http://192.168.30.19:8402/services/captcha/',
    //上传服务接口地址
    upload_url: 'http://113.247.237.98:10002/upload',
    system: "1",

    init: function () {
        requirejs(['utility/loading'], function () {
            var loading = new LoadingBox(getImagePath() + 'prettyPhoto/default/loader.gif');

            $.ajaxSetup({
                beforeSend: function () {
                    loading.show();
                },

                complete: function (event, xhr, options) {
                    loading.hide();

                    if (event.status != 200) {
                        var dlg = new ModalDlg();
                        dlg.show('errorResult', 'ajax异常', '获取数据失败');
                    }
                }
            });
        });

        var userInfo = this.getParameter('userInfo');
        var token = 'eyJraWQiOiJhZG1pbl8xIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJhZG1pbl8xIiwiYXVkIjpudWxsLCJleHAiOjE0ODEwODI1MzQsImp0aSI6IlRMdkdIZ2d0VFJsNnFURG40Y3JVWmciLCJpYXQiOjE0NDk5Nzg1MzQsInN1YiI6ImF1dGgiLCJleHRyYURhdGEiOm51bGwsInVzZXJJZCI6OSwic3lzdGVtSWQiOiIxIn0.sjbSn5Vu5NIR_XGgWQduTdm9ZZ5I0YOySeywXhWBttxJ5Fr_h2eOxHVQG4D8vCKmGj9DqBJk_G09tDaaqU4Xp118wwnMrEqY66VckYDIUKy4iZyjBV5e4hZPg1fylsgTNxvMoEKKlcIg3pQyLJoHcZunqkSMqDlytWWENwUd5N8TbJUDrkGM5lPqUYWKOjCbQC5lkXW8MdIRi5thKfuR0F6FsC9To0Jy50KcYS-KwyagDZv9klqq4BLqZBtMHlEgDEDhtT1DOF0NV4EPliAQCdqyIxOQGoe43SvkonIm5JCefj8tr2AQ8GMWSyvc7tIBBs-czeQntgMsXW8aiT3s7Q';

        if (userInfo != undefined) {
            token = userInfo.token;
        }

        $.ajaxSetup({
            headers: {
                "auth-token": token
            }
        });
    },

    getParameter: function (param, defaultValue) {
        var value = window.sessionStorage.getItem(param);

        if (value == undefined) {
            return defaultValue;
        }

        try {
            var obj = JSON.parse(value);

            if (obj != null) {
                return obj;
            } else {
                return value;
            }
        } catch (e) {
            console.log(e);
            return value;
        }
    },

    setParameter: function (param, value) {
        if (value == undefined) {
            return;
        }

        if (value instanceof Object) {
            window.sessionStorage.setItem(param, JSON.stringify(value));
        } else {
            window.sessionStorage.setItem(param, value);
        }
    },

    getLocalParam: function (param, defaultValue) {
        var value = window.localStorage.getItem(param);

        if (value == undefined) {
            return defaultValue;
        }

        try {
            var obj = JSON.parse(value);

            if (obj != null) {
                return obj;
            } else {
                return value;
            }
        } catch (e) {
            console.log(e);
            return value;
        }
    },

    setLocalParam: function(param, value) {
        if (value == undefined) {
            return;
        }

        if (value instanceof Object) {
            window.localStorage.setItem(param, JSON.stringify(value));
        } else {
            window.localStorage.setItem(param, value);
        }
    },


    delParameter: function (param) {
        window.sessionStorage.removeItem(param);
    },

    delLocalParam: function (param) {
        window.localStorage.removeItem(param);
    },

    initMenu: function () {
        requirejs(['utility/checkauth'], function () {
            new CheckAuth().getAuth();
        });
    }
});

var global = new Global();