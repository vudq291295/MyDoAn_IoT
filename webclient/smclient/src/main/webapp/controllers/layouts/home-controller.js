
define(['angular'], function (angular) {
    var app = angular.module('homeModule', []);
    app.factory('homeService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/room';
        var result = {
            paging: function () {
                return $http.get(serviceUrl + '/getAllRoom');
            }
        }
        return result;
    }]);
    app.controller('homeCtrl', ['$scope','homeService',function ($scope,homeService) {
    	function filterData() {
    		homeService.paging().then(function(response) {
    			console.log(response);
				
			},function() {
				
			})
		};
		filterData();
    }]);
    return app;
});

