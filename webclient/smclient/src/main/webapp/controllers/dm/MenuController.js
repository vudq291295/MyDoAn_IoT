define(['angular'], function (angular) {
    var app = angular.module('menuModule', []);
    app.factory('menuService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/menu';
        var result = {
    		getAllMenu: function () {
                return $http.get(serviceUrl + '/getAllMenu');
            }
        }
        return result;
    }]);
    app.controller('menuCtrl', ['$scope','homeService',function ($scope,homeService) {
//    	function filterData() {
//    		homeService.paging().then(function(response) {
//    			console.log(response);
//				
//			},function() {
//				
//			})
//		};
//		filterData();
    }]);
    return app;
});

