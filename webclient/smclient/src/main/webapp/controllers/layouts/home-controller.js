
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


    app.controller('homeCtrl', ['$scope','homeService','configService','MqttClient',function ($scope,homeService,configService,MqttClient) {
        var ip = "192.168.1.99";
        var port = 11884;
        var id = "test";
//        var client = new Paho.MQTT.Client(ip, port, id);

        MqttClient.init(ip, port, id);
        MqttClient.connect({onSuccess: successCallback,userName:"vudq",password:"1",onFailure:failedCallback});

        function failedCallback() {
        	console.log("failedddddddddddd");
        }
//        
        // called when a message arrives
        function onMessageArrived(message) {
        	console.log(message.destinationName,message.payloadString);
        
        }
        function successCallback() {
          MqttClient.subscribe('DV1/#');
          message = new Paho.MQTT.Message("Hello");
          message.destinationName = "DV1/123";
          MqttClient.send(message);
        }
        
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

