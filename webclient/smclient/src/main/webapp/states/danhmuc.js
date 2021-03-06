define([
], function () {
    var layoutUrl = "/smclient/views/danhmuc/";
    var controlUrl = "/smclient/controllers/dm/";
    var states = [
        {
            name: 'dmPhong',
            url: '/dmPhong',
            parent: 'layout',
            abstract: false,
            views: {
                'viewMain@root': {
                    templateUrl: layoutUrl + "dmPhong/index.html",
                    controller: "DmPhongViewCtrl as ctrl"
                }
            },
            moduleUrl: controlUrl + "DmPhongController.js"
        },
        {
            name: 'dmKichBan',
            url: '/dmKichBan',
            parent: 'layout',
            abstract: false,
            views: {
                'viewMain@root': {
                    templateUrl: layoutUrl + "dmKichBan/index.html",
                    controller: "dmKichBanViewCtrl as ctrl"
                }
            },
            moduleUrl: controlUrl + "DmKichBanController.js"
        },
        {
	        name: 'dmThietBi',
	        url: '/dmThietBi',
	        parent: 'layout',
	        abstract: false,
	        views: {
	            'viewMain@root': {
	                templateUrl: layoutUrl + "dmThietBi/index.html",
	                controller: "dmThietBiViewCtrl as ctrl"
	            }
	        },
	        moduleUrl: controlUrl + "DmThietBiController.js"
	    },
    ];
    return states;
});