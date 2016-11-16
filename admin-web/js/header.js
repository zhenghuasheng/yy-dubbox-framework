/**
 * Created by wunan on 15-12-14.
 */
(function () {
    function loadHeader() {
        var xmlhttp;
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("body-nav").innerHTML = xmlhttp.responseText;
            }
        };

        xmlhttp.open("GET", "./header.html", true);
        xmlhttp.send();
    }

    function loadFooter() {
        var xmlhttp;
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var footerDom = document.getElementById("body-footer");

                if (footerDom != undefined) {
                    footerDom.innerHTML = xmlhttp.responseText;
                }
            }
        };

        xmlhttp.open("GET", "./footer.html", true);
        xmlhttp.send();
    }

    loadHeader();
    loadFooter();
})();

function logoff() {
    window.sessionStorage.removeItem('userInfo');
    window.sessionStorage.removeItem('authInfo');
    window.sessionStorage.removeItem('navItem');
    window.sessionStorage.removeItem('car');
    window.sessionStorage.removeItem('carset');
    window.sessionStorage.removeItem('resourceInfo');
    window.sessionStorage.removeItem('roleInfo');
    window.sessionStorage.removeItem('userEdit');
    window.location.href = './login.html';
}


