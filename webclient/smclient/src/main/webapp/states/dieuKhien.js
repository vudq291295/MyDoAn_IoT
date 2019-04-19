define([
], function () {
    var layoutUrl = "/smclient/views/dieuKhien/";
    var controlUrl = "/smclient/controllers/dieuKhien/";
    var layoutUrl_laplich = "/smclient/views/laplich/";
    var controlUrl_laplich = "/smclient/controllers/laplich/";
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
        //lap lich
        {
            name: 'lapLichTB',
            url: '/lapLichTB',
            parent: 'layout',
            abstract: false,
            views: {
                'viewMain@root': {
                    templateUrl: layoutUrl_laplich + "lapLichTB/index.html",
                    controller: "lapLichThietBiViewCtrl as ctrl"
                }
            },
            moduleUrl: controlUrl_laplich + "lapLichThietBi.js"
        },

        
    ];
    return states;
});