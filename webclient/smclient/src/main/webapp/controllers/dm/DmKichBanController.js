define(['angular','ui-bootstrap', 'controllers/dm/DmPhongController', 'controllers/dm/DmThietBiController'], function (angular) {
    'use strict';
    var app = angular.module('dmKichBanModule', ['ui.bootstrap','dmPhongModule','dmThietBiModule']);
    app.factory('dmKichBanService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/script';
        var result = {
    		getAllScript: function (data) {
                return $http.post(serviceUrl + '/getAllScript',data);
            },
            getDetailsScript: function (data) {
                return $http.get(serviceUrl + '/getDetailsScript/'+data);
            },
            insertScript : function (data) {
                return $http.post(serviceUrl + '/insertScript',data);
            },
            updateScript : function (data) {
                return $http.post(serviceUrl + '/updateScript',data);
            },
            deleteScript : function (data) {
                return $http.post(serviceUrl + '/deleteScript',data);
            },
        }
        return result;
    }]);
    
    app.controller('dmKichBanViewCtrl', ['$scope','dmKichBanService','configService','userService','dmPhongService','$uibModal',
    	function ($scope,service,configService,userService,dmPhongService,$uibModal) {
		var currentUser = userService.GetCurrentUser();
		//connect MQTT
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
	        function onMessageArrived(message) {
	        	
	        	
	        }
	        function onConnectionLost(resp) {
	            console.log("Angular-Paho: Connection lost on ", client._id, ", error code: ", resp);
	            client._isConnected = false;
	          }
	
	        function successCallback() {
	      	  client.subscribe('DV1/#');
	          var message = new Paho.MQTT.Message("Hello");
	          message.destinationName = "DV1/123";
	          client.send(message);
	        }
	        
	        $scope.controlEqiup = function (item){
        		service.getDetailsScript(item.id).then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data) {
                        var data = angular.copy(response.data.data.details);
                        for(var i =0;i<data.length;i++){
            	            var message = new Paho.MQTT.Message(data[i].status+"");
            	            message.destinationName = "DV1/"+data[i].equipmentChanel+"/"+data[i].equipmentPort;
            	            client.send(message);
	            	      	  console.log(data[i].status+"");
                        }
                    } else {
                        console.log(response);
                    }
                }, function (response) {
                    console.log(response);
                });
	        }
	        
        $scope.config = {
                label: angular.copy(configService.label)
        }
        $scope.search = {};

    	function filterData() {
    		service.getAllScript($scope.search).then(function (response) {
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
		
        $scope.doSearch = function () {
            filterData();
        };

        $scope.refresh = function () {
            filterData();
        };
        
        $scope.create = function () {
        	var a = configService.buildUrl('danhmuc/dmKichBan', 'add');
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                size: 'lg',
                windowClass : 'show app-modal-window',
            	templateUrl: a ,
                controller: 'dmKichBanCreateCtrl',
                resolve: {}
            });

            modalInstance.result.then(function (updatedData) {
                $scope.refresh();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
        $scope.edit = function (target) {
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                size: 'lg',
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('danhmuc/dmKichBan', 'edit'),
                controller: 'dmKichBanEditCtrl',
                resolve: {
                    targetData: function () {
                        return target;
                    }
                }
            });
            modalInstance.result.then(function (updatedData) {
                var index = $scope.data.indexOf(target);
                if (index !== -1) {
                    $scope.data[index] = updatedData;
                }
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
        $scope.detail = function (target) {
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                size: 'lg',
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('danhmuc/dmKichBan', 'detail'),
                controller: 'dmKichBanDetailCtrl',
                resolve: {
                    targetData: function () {
                        return target;
                    }
                }
            });
            modalInstance.result.then(function (updatedData) {
                var index = $scope.data.indexOf(target);
                if (index !== -1) {
                    $scope.data[index] = updatedData;
                }
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.delete = function (target) {
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('danhmuc/dmKichBan', 'delete'),
                controller: 'dmKichBanDeleteCtrl',
                resolve: {
                    targetData: function () {
                        return target;
                    }
                }
            });
            modalInstance.result.then(function (updatedData) {
                var index = $scope.data.indexOf(target);
                if (index !== -1) {
                    $scope.data.splice(index, 1);
                }
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };


    }]);
    
    app.controller('dmKichBanCreateCtrl', ['$scope', '$uibModalInstance', '$location', 'dmKichBanService', 'configService','dmPhongService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService,dmPhongService, $state, tempDataService, $filter, $uibModal, $log, ngNotify) {
            $scope.config = angular.copy(configService);
            $scope.tempData = tempDataService.tempData;
            $scope.target = {};
            $scope.target.details = [];
            $scope.data= [];
            $scope.newItem = {};
            $scope.paged = angular.copy(configService.pageDefault);
            
            $scope.isLoading = false;
            $scope.title = function () { return 'Thêm mới kịch bản'; };
//            $scope.lstPhong = [];
            function loadDataPhong (){
            	dmPhongService.getAllRoom().then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data.length > 0) {
                        $scope.lstPhong = angular.copy(response.data.data);
                        console.log($scope.lstPhong);
                    } else {
                        console.log(response);
                    }
                }, function (response) {
                    console.log(response);
                });      
        	}
            loadDataPhong ();
            
            
            $scope.addRow = function (obj) {
                if (!obj.equipmentID) {
                    ngNotify.set("Hãy chọn phòng", { duration: 2000, type: 'error' });
                    return;
                }
                $scope.target.details.push($scope.newItem);
                $scope.isListItemNull = false;
                $scope.pageChanged();
                $scope.newItem = {};
            };

            $scope.removeItem = function (obj) {
                var index = $scope.data.indexOf(obj);
                $scope.target.details.splice(index, 1);
                $scope.pageChanged();
            }

            $scope.pageChanged = function () {
                var currentPage = $scope.paged.currentPage;
                var itemsPerPage = $scope.paged.itemsPerPage;
                $scope.paged.totalItems = $scope.target.details.length;
                $scope.data = [];
                if ($scope.target.details) {
                    for (var i = (currentPage - 1) * itemsPerPage; i < currentPage * itemsPerPage && i < $scope.target.details.length; i++) {
                        $scope.data.push($scope.target.details[i])
                    }
                }
            }

            
            $scope.changeStatus = function(item){
            	item.status = !item.status;
            }
            
            $scope.selectedThietBi = function (strKey) {
                    var modalInstance = $uibModal.open({
                        backdrop: 'static',
                        windowClass : 'show app-modal-window',
                        templateUrl: configService.buildUrl('danhmuc/dmThietBi', 'selectMultiData'),
                        controller: 'dmThietBiSelectMultiData',
                        resolve: {
                        	targetData : function(){
                        		return $scope.target.details;
                        	},
                        }
                    });
                    modalInstance.result.then(function (updatedData) {
                    	console.log(updatedData);
                    	if(updatedData){
                    	var lstData = [];
	                        angular.forEach(updatedData, function (value, key) {
	                        	console.log(value.id);
	                            var tmp = $filter('filter')($scope.target.details, { equipmentID: value.id }, true);
	                            console.log(tmp);
	                            if(tmp.length == 0){
	                            	var temp = {};
	                            	temp.equipmentID = value.id;
	                            	temp.equipmentName = value.name;
	                            	temp.roomName = value.nameRoom;
	                            	temp.equipmentPort = value.portOutput;
	                            	temp.equipmentChanel = value.chanel;
	                            	temp.roomID = value.roomId;
	                            	temp.status = true;
	                            	lstData.push(temp);
	                            }
	                            else if(tmp.length == 1){
	                            	lstData.push(tmp[0]);
	                            }
	                        });
	                        console.log(lstData);
	                        $scope.target.details = angular.copy(lstData);
	                    	$scope.pageChanged();
                    	}
                    }, function () {

                    });
            }

            
            $scope.save = function () {
//            	$scope.target.chanel = $scope.target.chanel+"";
            	var myJSON = JSON.stringify($scope.target);
            	for(var i = 0; i<$scope.target.details.length ;i++){
            		console.log(i);
            		$scope.target.details[i].equipmentID = $scope.target.details[i].equipmentID+"";
            		$scope.target.details[i].equipmentName = $scope.target.details[i].equipmentName+"";
            		$scope.target.details[i].roomName = $scope.target.details[i].roomName+"";
            		$scope.target.details[i].equipmentPort = $scope.target.details[i].equipmentPort+"";
            		$scope.target.details[i].equipmentChanel = $scope.target.details[i].equipmentChanel+"";
            		$scope.target.details[i].roomID = $scope.target.details[i].roomID+"";
            		if($scope.target.details[i].status){
            			$scope.target.details[i].status ="1";
            		}
            		else{
            			$scope.target.details[i].status ="0";
            		}
            	}
            	console.log($scope.target);
                service.insertScript($scope.target).then(function (successRes) {
                	console.log(successRes);
                    if (successRes && successRes.status === 200 && !successRes.data.error) {
                        ngNotify.set(successRes.data.message, { type: 'success' });
                        $uibModalInstance.close($scope.target);
                    } else {
                        console.log('successRes', successRes);
                        ngNotify.set(successRes.data.message, { duration: 3000, type: 'error' });
                    }
                },
				function (errorRes) {
                	if(errorRes.data){
                        ngNotify.set(errorRes.data.message, { duration: 3000, type: 'error' });
                	}
				    console.log('errorRes 12', errorRes);

				});
            };
            $scope.cancel = function () {
                $uibModalInstance.close(); 
            };
        }]);

    app.controller('dmKichBanEditCtrl', ['$scope', '$uibModalInstance', '$location', 'dmKichBanService', 'configService','dmPhongService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService,dmPhongService, $state, tempDataService, $filter, $uibModal, $log, targetData, ngNotify) {
            $scope.target = targetData;
            $scope.tempData = tempDataService.tempData;
            $scope.config = angular.copy(configService);
            $scope.targetData = angular.copy(targetData);
            $scope.isLoading = false;
            $scope.paged = angular.copy(configService.pageDefault);
            $scope.title = function () { return 'Cập nhật kịch bản'; };
            function loadDataPhong (){
            	dmPhongService.getAllRoom().then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data.length > 0) {
                        $scope.lstPhong = angular.copy(response.data.data);
                        console.log($scope.lstPhong);
                    } else {
                        console.log(response);
                    }
                }, function (response) {
                    console.log(response);
                });      
        	}
            loadDataPhong ();

            $scope.addRow = function (obj) {
                if (!obj.equipmentID) {
                    ngNotify.set("Hãy chọn phòng", { duration: 2000, type: 'error' });
                    return;
                }
                $scope.target.details.push($scope.newItem);
                $scope.isListItemNull = false;
                $scope.pageChanged();
                $scope.newItem = {};
            };

            $scope.removeItem = function (obj) {
                var index = $scope.data.indexOf(obj);
                $scope.target.details.splice(index, 1);
                $scope.pageChanged();
            }

            $scope.pageChanged = function () {
                var currentPage = $scope.paged.currentPage;
                var itemsPerPage = $scope.paged.itemsPerPage;
                $scope.paged.totalItems = $scope.target.details.length;
                $scope.data = [];
                if ($scope.target.details) {
                    for (var i = (currentPage - 1) * itemsPerPage; i < currentPage * itemsPerPage && i < $scope.target.details.length; i++) {
                        $scope.data.push($scope.target.details[i])
                    }
                }
            }

            
            $scope.changeStatus = function(item){
            	item.status = !item.status;
            }
            
            $scope.selectedThietBi = function (strKey) {
                    var modalInstance = $uibModal.open({
                        backdrop: 'static',
                        windowClass : 'show app-modal-window',
                        templateUrl: configService.buildUrl('danhmuc/dmThietBi', 'selectMultiData'),
                        controller: 'dmThietBiSelectMultiData',
                        resolve: {
                        	targetData : function(){
                        		return $scope.target.details;
                        	},
                        }
                    });
                    modalInstance.result.then(function (updatedData) {
                    	console.log(updatedData);
                    	if(updatedData){
                    	var lstData = [];
	                        angular.forEach(updatedData, function (value, key) {
	                        	console.log(value.id);
	                            var tmp = $filter('filter')($scope.target.details, { equipmentID: value.id }, true);
	                            console.log(tmp);
	                            if(tmp.length == 0){
	                            	var temp = {};
	                            	temp.equipmentID = value.id;
	                            	temp.equipmentName = value.name;
	                            	temp.roomName = value.nameRoom;
	                            	temp.equipmentPort = value.portOutput;
	                            	temp.equipmentChanel = value.chanel;
	                            	temp.roomID = value.roomId;
	                            	temp.status = true;
	                            	lstData.push(temp);
	                            }
	                            else if(tmp.length == 1){
	                            	lstData.push(tmp[0]);
	                            }
	                        });
	                        console.log(lstData);
	                        $scope.target.details = angular.copy(lstData);
	                    	$scope.pageChanged();
                    	}
                    }, function () {

                    });
            }

            $scope.save = function () {
            	var postData = {};
            	postData.id = $scope.target.id;
            	postData.roomId = $scope.target.roomId;
            	postData.name= $scope.target.name;
            	postData.status= $scope.target.status;
            	postData.portOutput= $scope.target.portOutput;

                service.updateScript(postData).then(function (successRes) {
                    if (successRes && successRes.status == 200 && !successRes.data.Error) {
                        ngNotify.set(successRes.data.Message, { type: 'success' });
                        $uibModalInstance.close($scope.target);
                    } else {
                        console.log('successRes', successRes);
                        ngNotify.set(successRes.data.Message, { duration: 3000, type: 'error' });
                    }
                },
                    function (errorRes) {
                        console.log('errorRes', errorRes);
                    });
            };

        	function filterData() {
        		service.getDetailsScript($scope.target.id).then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data) {
                        $scope.target = angular.copy(response.data.data);
                        console.log($scope.target);
                        $scope.pageChanged();
                    } else {
                        console.log(response);
                    }
                }, function (response) {
                    console.log(response);
                });
    		};
    		filterData();
    		
            $scope.pageChanged = function () {
                var currentPage = $scope.paged.currentPage;
                var itemsPerPage = $scope.paged.itemsPerPage;
                $scope.paged.totalItems = $scope.target.details.length;
                $scope.data = [];
                if ($scope.target.details) {
                    for (var i = (currentPage - 1) * itemsPerPage; i < currentPage * itemsPerPage && i < $scope.target.details.length; i++) {
                        $scope.data.push($scope.target.details[i])
                    }
                }
            }
            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);
    app.controller('dmKichBanDetailCtrl', ['$scope', '$uibModalInstance', '$location', 'dmKichBanService', 'configService','dmPhongService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData',
	function ($scope, $uibModalInstance, $location, service, configService,dmPhongService, $state, tempDataService, $filter, $uibModal, $log, targetData) {
        $scope.config = {
                label: angular.copy(configService.label)
        }
    	$scope.tempData = tempDataService.tempData;
	    $scope.config = tempDataService.config;
	    $scope.target = targetData;
	    $scope.title = function () { return 'Chi tiết kịch bản'; };
        function loadDataPhong (){
        	dmPhongService.getAllRoom().then(function (response) {
            	console.log(response);
                if (response && response.data && response.data.data.length > 0) {
                    $scope.lstPhong = angular.copy(response.data.data);
                    console.log($scope.lstPhong);
                } else {
                    console.log(response);
                }
            }, function (response) {
                console.log(response);
            });      
    	}
        loadDataPhong ();

    	function filterData() {
    		service.getDetailsScript($scope.target.id).then(function (response) {
            	console.log(response);
                if (response && response.data && response.data.data) {
                    $scope.target = angular.copy(response.data.data);
                    console.log($scope.target);
                } else {
                    console.log(response);
                }
            }, function (response) {
                console.log(response);
            });
		};
		filterData();

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };


	}]);
    app.controller('dmKichBanDeleteCtrl', ['$scope', '$uibModalInstance', '$location', 'dmKichBanService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService, $state, tempDataService, $filter, $uibModal, $log, targetData, ngNotify) {
            $scope.target = targetData;
            $scope.config = angular.copy(configService);
            $scope.targetData = angular.copy(targetData);
            $scope.isLoading = false;

            $scope.title = function () { return 'Bạn có muốn xóa '+$scope.target.name; };

            $scope.save = function () {
            	var postData = {};
            	postData.id = $scope.target.id;
            	postData.roomId = $scope.target.roomId;
            	postData.name= $scope.target.name;
            	postData.status= $scope.target.status;
            	postData.portOutput= $scope.target.portOutput;
                service.deleteScript(postData).then(function (successRes) {
                    if (successRes && successRes.status == 200 && !successRes.data.Error) {
                        ngNotify.set(successRes.data.Message, { type: 'success' });
                        $uibModalInstance.close($scope.target);
                    } else {
                        console.log('successRes', successRes);
                        ngNotify.set(successRes.data.Message, { duration: 3000, type: 'error' });
                    }
                },
                    function (errorRes) {
                        console.log('errorRes', errorRes);
                    });
            };

            $scope.close = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);

    return app;
});

