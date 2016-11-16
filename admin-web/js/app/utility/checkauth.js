/**
 * Created by wunan on 15-12-4.
 */
var CheckAuth = Class.extend({
    init: function () {
    },

    getAuth: function () {
        var userInfo = global.getParameter('userInfo');

        if (userInfo == undefined) {
            return null;
        }

        var authInfo = global.getParameter("authInfo");

        if (authInfo == undefined) {
            $.ajax({
                type: 'get',
                url: global.auth_url + 'list/1',
                model: this,

                success: function (result) {
                    global.setParameter("authInfo", result);
                    afterGetAuth(result);
                }
            });
        } else {
            afterGetAuth(authInfo);
        }

        function afterGetAuth(result) {
            if (!result.succeed) {
                var dlg = new ModalDlg();
                dlg.show('authResult', '权限认证错误', result.description);
                return;
            }

            var resources = result.object;
            var rootMenu = [];

            for (var i = 0; i < resources.length; ++i) {
                var resource = resources[i];

                if (resource.pitemid == 0) {
                    rootMenu[resource.itemid] = resource;
                }
            }

            for (i = 0; i < resources.length; ++i) {
                resource = resources[i];
                var parent = rootMenu[resource.pitemid];

                if (parent == null) {
                    continue;
                }

                if (parent.sub == undefined) {
                    parent.sub = [];
                }

                parent.sub[parent.sub.length] = resource;
            }

            rootMenu.sort(function (m1, m2) {
                if (m1.vieworder == undefined) {
                    return 1;
                }

                if (m2.vieworder == undefined) {
                    return -1;
                }

                if (m1.vieworder < m2.vieworder) {
                    return -1;
                } else {
                    return 1;
                }
            });

            var innerHtml = '';

            for (itemid in rootMenu) {
                var menu = rootMenu[itemid];

                if (menu.sub == undefined) {
                    innerHtml += '<li id="' + menu.rspath + '">' +
                        '<a href="' + getRootPath() + menu.url + '">' +
                        '<i class="' + menu.icon + '"></i>' +
                        menu.name + '</a></li>';
                } else {
                    innerHtml += '<li id="' + menu.rspath + '" class="has_sub">' +
                        '<a href="#"><i class="' + menu.icon + '"></i>' +
                        menu.name + '<span class="pull-right">' +
                        '<i class="icon-chevron-right"></i></span></a><ul>';

                    for (subId in menu.sub) {
                        var subMenu = menu.sub[subId];
                        innerHtml += '<li id="' + subMenu.rspath + '">' +
                            '<a href="' + getRootPath() + subMenu.url + '">' + subMenu.name + '</a></li>';
                    }

                    innerHtml += '</ul></li>';
                }
            }

            $('#nav').html(innerHtml);
            var selMenu = global.getParameter('navItem');

            if (selMenu == undefined) {
                for (itemid in rootMenu) {
                    selMenu = rootMenu[itemid].rspath;
                    break;
                }
            }

            $('#' + selMenu + ' > a').addClass('open');
            initMenu();
        }

        function initMenu() {
            $("#nav > li > a").on('click', function (e) {
                global.setParameter("navItem", this.parentElement.id);

                if ($(this).parent().hasClass("has_sub")) {
                    e.preventDefault();
                }

                if (!$(this).hasClass("subdrop")) {
                    // hide any open menus and remove all other classes
                    $("#nav li ul").slideUp(350);
                    $("#nav li a").removeClass("subdrop");

                    // open our new menu and add the open class
                    $(this).next("ul").slideDown(350);
                    $(this).addClass("subdrop");
                }

                else if ($(this).hasClass("subdrop")) {
                    $(this).removeClass("subdrop");
                    $(this).next("ul").slideUp(350);
                }
            });

            $(".sidebar-dropdown a").on('click', function (e) {
                e.preventDefault();

                if (!$(this).hasClass("open")) {
                    // hide any open menus and remove all other classes
                    $(".sidebar #nav").slideUp(350);
                    $(".sidebar-dropdown a").removeClass("open");

                    // open our new menu and add the open class
                    $(".sidebar #nav").slideDown(350);
                    $(this).addClass("open");
                }

                else if ($(this).hasClass("open")) {
                    $(this).removeClass("open");
                    $(".sidebar #nav").slideUp(350);
                }
            });
        }
    }
});