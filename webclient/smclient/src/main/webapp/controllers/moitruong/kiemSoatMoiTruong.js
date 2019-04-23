define(['angular','controllers/dm/DmThietBiController', 'controllers/dm/DmPhongController'], function (angular) {
    var app = angular.module('kiemSoatMoiTruongModule', ['dmThietBiModule','dmPhongModule']);
    app.factory('kiemSoatMoiTruongService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/environment';
        var result = {
            paging: function () {
                return $http.post(serviceUrl + '/getAllEnviroment');
            }
        }
        return result;
    }]);
    
    
    app.controller('kiemSoatMoiTruongCtrl', ['$scope','$interval','kiemSoatMoiTruongService','configService','userService','dmThietBiService','dmPhongService',
    	function ($scope,$interval,service,configService,userService,dmThietBiService,dmPhongService) {
    	$scope.filtered = {};
    	$scope.search = {};

		var currentUser = userService.GetCurrentUser();
		console.log(currentUser);
		var ip = configService.rootUrlWS;
		var port = configService.rootPortWS;
		var id = "test123";
      var client = new Paho.MQTT.Client(ip, port, id);
      client.onConnectionLost = onConnectionLost;
      client.onMessageArrived = onMessageArrived;
      client.connect({onSuccess: successCallback,userName:currentUser.userName,password:currentUser.passWord,onFailure:failedCallback});
	
      function failedCallback() {
      	console.log("failedddddddddddd");
      }
//      
      // called when a message arrives
      function onMessageArrived(message) {
    	  var lstTopic = message.destinationName.split('/');
    	  console.log(lstTopic);
    	  for(var i = 0;i<$scope.data.length;i++){
    		  if($scope.data[i].chanel == lstTopic[1]){
    			  if($scope.data[i].portOutput == lstTopic[2]){
    				  console.log($scope.data[i]);
    				//  $scope.data[i].status = parseInt(message.payloadString);
    			  }
    		  }
    	  }
      	console.log(message.destinationName,message.payloadString);
      }
      
      var auto = $interval(function() {
    	  $scope.data = $scope.data;
        }, 100);
      
      function onConnectionLost(resp) {
          console.log("Angular-Paho: Connection lost on ", client._id, ", error code: ", resp);
          client._isConnected = false;
        }

      function successCallback() {
    	  client.subscribe('DV1/#');
        message = new Paho.MQTT.Message("Hello");
        message.destinationName = "DV1/123";
        client.send(message);
      }
      
      $scope.controlEqiup = function (target){
    	  var mss = "0";
    	  if(!target.status){
    		  mss = "1";
    	  }
          message = new Paho.MQTT.Message(mss);
          message.destinationName = "DV1/"+target.chanel+"/"+target.portOutput;
          client.send(message);
    	  console.log(target);
      }

      $scope.changeRoom = function(){
    	  if($scope.filtered.MaPhong == -1){
    		  filterData();
    	  }
    	  else{
  	  		dmThietBiService.getEpuipmentByRoom($scope.filtered.MaPhong).then(function (response) {
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
  			}
      }
      
	  	function filterData() {
	  		service.paging({}).then(function (response) {
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
		$scope.doSearch = function(){
			filterData();
		}
    }]);

    return app;
});
