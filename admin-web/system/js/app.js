function getCurrentPath() {
    var strUrl = window.location.href;
    var arrUrl = strUrl.split("/");
    var page = arrUrl[arrUrl.length - 1];
    return page.substr(0, page.lastIndexOf('.'));
}

function getImagePath() {
    return '../img/';
}

function getRootPath() {
    return '../';
}

requirejs.config({
    //By default load any module IDs from js/lib
    baseUrl: 'js/app/' + getCurrentPath(),
    //except, if the module ID starts with "app",
    //load it from the js/app directory. paths
    //config is relative to the baseUrl, and
    //never includes a ".js" extension since
    //the paths config could be for a directory.
    paths: {
        config: '../../../../js/config',
        lib: '../../../../js',
        utility: '../../../../js/app/utility'
    }
});

// Start the main app logic.
requirejs(['lib/jsi'], function () {
    requirejs(['controller', 'model', 'view', 'config/global'], function (controller) {
        controller.setup();
    });
});

