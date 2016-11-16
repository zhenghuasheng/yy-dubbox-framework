/**
 * Created by wunan on 15-12-7.
 */
var PageData = Class.extend({
    init: function (option) {
        this.option = option;

        if (this.option.page == undefined) {
            this.option.page = 1;
        }

        if (this.option.limit == undefined) {
            this.option.limit = 10;
        }

        if (option.prePageId != undefined) {
            $('#' + option.prePageId).click(this, function (event) {
                event.data.loadPre();
            });
        }

        if (option.nextPageId != undefined) {
            $('#' + option.nextPageId).click(this, function (event) {
                event.data.loadNext();
            });
        }

        if (option.curPageId != undefined) {
            this.appendModal(option.curPageId);
            var submitId = '#' + option.curPageId + 'SubmitPage';
            var modalId = '#' + option.curPageId + 'Modal';
            var pageNumId = '#' + option.curPageId + '_pageNum';
            $(modalId).find('.modal-dialog').css('width', '300px');

            $(submitId).click(this, function(event) {
                $(modalId).modal('hide');

                if (event.data.option.url == undefined) {
                    return;
                }

                var page = parseInt($(pageNumId).val()) - 1;
                event.data.refresh(page);
            });

            $('#' + option.curPageId).click(this, function(event) {
                $(pageNumId).val(event.data.option.page);
                $(modalId).modal('show');
            });
        }
    },

    load: function(url, page, limit, callback) {
        $.ajax({
            type: 'get',
            url: url + "/" + (page - 1) * limit + "/" + limit,
            data: {condi:this.option.condition},
            model: this,

            success: function (result) {
                var option = this.model.option;

                if (result.succeed) {
                    option.page = page;
                    option.limit = limit;
                    option.url = url;
                    option.callback = callback;
                    option.object = result.object;

                    if (option.curPageId != undefined) {
                        $('#' + option.curPageId).html(option.page);
                    }
                }

                this.model.callResult(result, page, limit);
            }
        })
    },

    loadPre: function() {
        if ((this.option.page == undefined) || (this.option.limit == undefined)) {
            this.callResult({succed: false, description: '无效的页面参数'});
            return;
        }

        if (this.option.page == 1) {
            this.callResult({succed: false, description: '已经到了第一页'});
            return;
        }

        this.load(this.option.url, this.option.page - 1, this.option.limit, this.option.callback);
    },

    loadNext: function() {
        if ((this.option.page == undefined) || (this.option.limit == undefined)) {
            this.callResult({succed: false, description: '无效的页面参数'});
            return;
        }

        this.load(this.option.url, this.option.page + 1, this.option.limit, this.option.callback);
    },

    refresh: function (page, url) {
        if (url != undefined) {
            this.option.url = url;
        }

        if (page != undefined) {
            this.option.page = page + 1;
        }

        this.load(this.option.url, this.option.page, this.option.limit, this.option.callback);
    },

    callResult: function (result) {
        if (!result.succeed) {
            var dlg = new ModalDlg();

            if (result.ptError == 'PT_ERROR_NODATA') {
                dlg.show('ResultFindResult', '加载出错啦', '已经到了最后一页');
            } else {
                dlg.show('ResultFindResult', "加载出错啦", result.description);
            }
        }

        this.option.callback(result, this.option.page, this.option.limit);
    },

    appendModal: function(pageId) {
        var html = '<div id="' + pageId + 'Modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal"aria-hidden="true">×' +
            '</button>' +
            '<h4 class="modal-title">选择跳转页面</h4>' +
            '</div>' +
            '<div class="modal-body">' +
            '<div class="form-horizontal">' +
            '<div class="form-group">' +
            '<label class="control-label col-lg-3" for="' + pageId + '_pageNum">页码</label>' +
            '<div class="col-lg-6">' +
            '<input type="text" class="form-control" id="' + pageId + '_pageNum">' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="modal-footer">' +
            '<button id="' + pageId + 'SubmitPage" type="button" class="btn btn-primary">提交</button>' +
            '<button type="button" class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
        $(document.body).append(html);
    }
});
