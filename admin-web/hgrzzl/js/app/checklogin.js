/**
 * Created by wunan on 15-12-4.
 */
(function () {
    if (window.sessionStorage.getItem('userInfo') == undefined) {
        window.location.href = '../login.html';
    }
})();