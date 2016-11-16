/**
 * Created by wunan on 15-12-8.
 */
var LoadingBox = Class.extend({
    init: function (pic) {
        if ($('#loading-box').length == 0) {
            var html = '<div id="loading-box">' +
                '<div id="loading-text">' +
                '<img src=' + pic + '>' +
                '<span>数据正在加载中...</span>' +
                '</div></div>';
            $(document.body).append(html);
            var css = {
                'position' : 'fixed',
                'top' : '0',
                'left': '0',
                'bottom':'0',
                'right':'0',
                'z-index': '1999',
                'display':'none'
            };

            $('#loading-box').css(css);

            css = {
                'width' : '10%',
                'height' : '35px',
                'line-height' : '35px',
                'position' : 'absolute',
                'top' : '0',
                'left' : '0',
                'bottom' :'0',
                'right' :'0',
                'z-index' :'999',
                'margin' : 'auto',
                'font-size' : '0.7rem',
                'text-align' : 'center',
                //'background' : '#fff'
            };

            $('#loading-text').css(css);
        }
    },

    show: function () {
        $('#loading-box').show();
    },

    hide: function() {
        $('#loading-box').hide();
    }
});