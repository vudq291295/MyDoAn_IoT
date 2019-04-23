define([
], function () {
    var layoutUrl = "/smclient/views/auth/";
    var controlUrl = "/smclient/controllers/auth/";
    var states = [
        {
            name: 'donVi',
            url: '/donVi',
            parent: 'layout',
            abstract: false,
            views: {
                'viewMain@root': {
                    templateUrl: layoutUrl + "donVi/index.html",
                    controller: "donViViewCtrl as ctrl"
                }
            },
            moduleUrl: controlUrl + "donViController.js"
        },
        {
            name: 'quanLyNguoiDung',
            url: '/quanLyNguoiDung',
            parent: 'layout',
            abstract: false,
            views: {
                'viewMain@root': {
                    templateUrl: layoutUrl + "nguoiDung/index.html",
                    controller: "nguoiDungViewCtrl as ctrl"
                }
            },
            moduleUrl: controlUrl + "NguoiDungController.js"
        },
    ];
    return states;
});