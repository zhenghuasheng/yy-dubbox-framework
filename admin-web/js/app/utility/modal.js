/**
 * Created by wunan on 15-12-4.
 */
var ModalDlg = Class.extend({
    init: function () {
    },
    show: function (id, title, content, callback) {
        var modalDiv = $('#' + id);

        if (modalDiv.length == 0) {
            modalDiv = addDiv();
        } else {
            $('#' + id + ' .modal-title').html(title);
            $('#' + id + ' .modal-body').html(content);
        }

        if (callback != undefined) {
            modalDiv.on('hide.bs.modal', callback);
        }

        modalDiv.modal('show');

        function addDiv() {
            var html = '<div id="' + id + '" class="modal fade" tabindex="-1"' +
                ' role="dialog" aria-hidden="true">' +
                '<div class="modal-dialog"><div class="modal-content">' +
                '<div class="modal-header"><button type="button" class="close" ' +
                'data-dismiss="modal" aria-hidden="true">×</button><h4 class="modal-title">' +
                title + '</h4></div><div class="modal-body"><p>' + content + '</p></div>' +
                '<div class="modal-footer">' +
                '<button id="modal-close" type="button" class="btn btn-default" ' +
                'data-dismiss="modal" aria-hidden="true">关闭</button>' +
                '</div></div></div></div>';

            $(document.body).append(html);
            return $('#' + id);
        }
    }
});
