define([
], function () {
    var layoutUrl = "/smclient/views/dieuKhien/";
    var controlUrl = "/smclient/controllers/dieuKhien/";
    var states = [
        {
            name: 'dieuKhienThietBi',
            url: '/dieuKhienThietBi',
            parent: 'layout',
            abstract: false,
            views: {
                'viewMain@root': {
                    templateUrl: layoutUrl + "dieuKhienThietBi/index.html",
                    controller: "dieuKhienThietBiCtrl as ctrl"
                }
            },
            moduleUrl: controlUrl + "dieuKhienThietBi.js"
        },
    ];
    return states;
});