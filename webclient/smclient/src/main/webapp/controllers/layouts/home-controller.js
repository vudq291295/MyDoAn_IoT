
define(['angular'], function (angular) {
    var app = angular.module('homeModule', ['chart.js']);

    app.factory('homeService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/room';
        var result = {
            paging: function () {
                return $http.get(serviceUrl + '/getAllRoom');
            }
        }
        return result;
    }]);


    app.controller('homeCtrl', ['$scope','homeService','configService','MqttClient',function ($scope,homeService,configService,MqttClient) {
        $scope.barColor2 = ['#3e95cd', '#8e5ea2'];

        $scope.options2 = {
                legend: { display: false },
                scales: {
                    yAxes: [
                      {
                          id: 'y-axis',
                          min: 0,
                          ticks: {
                              beginAtZero: true,
                              callback: function (value) {
                                  return (value + '').replace(/(\d)(?=(\d{3})+$)/g, '$1,');
                              }
                          }
                      }
                    ]
                }

            };
        
        $scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
        $scope.data = [300, 500, 100];
        $scope.labels2 = ["January", "February", "March", "April", "May", "June", "July"];

        $scope.data2 = [
            [65, 59, 80, 81, 56, 55, 40]          ];
                    

    	function filterData() {
		};
		filterData();
    }]);
    return app;
});

