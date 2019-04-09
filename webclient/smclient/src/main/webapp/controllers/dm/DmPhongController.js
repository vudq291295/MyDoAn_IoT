define(['angular'], function (angular) {
    var app = angular.module('dmPhongModule', []);
    app.factory('dmPhongService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/room';
        var result = {
    		getAllRoom: function () {
                return $http.get(serviceUrl + '/getAllRoom');
            }
        }
        return result;
    }]);
    app.controller('DmPhongViewCtrl', ['$scope','dmPhongService','configService',
    	function ($scope,service,configService) {
        $scope.config = {
                label: angular.copy(configService.label)
        }
    	function filterData() {
    		service.getAllRoom().then(function (response) {
            	console.log(response);
                if (response && response.data && response.data.data.length > 0) {
                    $scope.data = angular.copy(response.data.data);
                    console.log($scope.data);
                } else {
                    console.log(response);
                }
            }, function (response) {
                console.log(response);
            });
		};
		filterData();
    }]);
    return app;
});

