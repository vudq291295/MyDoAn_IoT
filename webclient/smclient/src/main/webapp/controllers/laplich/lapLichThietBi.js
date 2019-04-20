define(['angular', 'controllers/dm/DmThietBiController'], function (angular) {
    var app = angular.module('lapLichThietBiModule', ['dmThietBiModule']);
    app.factory('lapLichThietBiService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/schedule';
        var result = {
    		getAllScheduleEquip: function (data) {
                return $http.post(serviceUrl + '/getAllScheduleEquip',data);
            },
            insertSchedule : function (data) {
                return $http.post(serviceUrl + '/insertSchedule',data);
            },
            updateSchedule : function (data) {
                return $http.post(serviceUrl + '/updateSchedule',data);
            },
            deleteSchedule : function (data) {
                return $http.post(serviceUrl + '/deleteSchedule',data);
            },
        }
        return result;
    }]);
    app.controller('lapLichThietBiViewCtrl', ['$scope','$uibModal','lapLichThietBiService','configService','$uibModal','ngNotify',
    	function ($scope,$uibModal,service,configService,$uibModal,ngNotify) {
        $scope.config = {
                label: angular.copy(configService.label)
        }
        
        $scope.changeStatus = function(item){
        	var currentSTT = item.status;
        	if(item.status == 1){
        		item.status  = 0;
        	}
        	else{
        		item.status  = 1;
        	}
            service.updateSchedule(item).then(function (successRes) {
	                if (successRes && successRes.status == 200 && !successRes.data.Error) {
	                    ngNotify.set(successRes.data.message, { type: 'success' });
	                } else {
	                    console.log('successRes', successRes);
	                    item.status = currentSTT;
	                    ngNotify.set(successRes.data.message, { duration: 3000, type: 'error' });
	                }
	            },
                function (errorRes) {
		            item.status = currentSTT;
                    console.log('errorRes', errorRes);
            });
        }

        $scope.changeStatusEquip = function(item){
        	var currentSTT = item.status;
        	if(item.statusEquipment == 1){
        		item.statusEquipment  = 0;
        	}
        	else{
        		item.statusEquipment  = 1;
        	}
            service.updateSchedule(item).then(function (successRes) {
                if (successRes && successRes.status == 200 && !successRes.data.Error) {
                    ngNotify.set(successRes.data.message, { type: 'success' });
                } else {
                    console.log('successRes', successRes);
                    item.statusEquipment = currentSTT;
                    ngNotify.set(successRes.data.message, { duration: 3000, type: 'error' });
                }
            },
            function (errorRes) {
	            item.statusEquipment = currentSTT;
                console.log('errorRes', errorRes);
        });

        }

        $scope.search = {};
    	function filterData() {
    		service.getAllScheduleEquip($scope.search).then(function (response) {
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
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                size: 'md',
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('laplich/lapLichTB', 'add'),
                controller: 'lapLichThietBiCreateCtrl',
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
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('laplich/lapLichTB', 'edit'),
                controller: 'lapLichThietBiEditCtrl',
                resolve: {
                    targetData: function () {
                        return target;
                    }
                }
            });
            modalInstance.result.then(function (updatedData) {
                $scope.refresh();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
        $scope.detail = function (target) {
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('laplich/lapLichTB', 'detail'),
                controller: 'lapLichThietBiDetailCtrl',
                resolve: {
                    targetData: function () {
                        return target;
                    }
                }
            });
            modalInstance.result.then(function (updatedData) {
                $scope.refresh();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.delete = function (target) {
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                windowClass : 'show app-modal-window',
                templateUrl: configService.buildUrl('laplich/lapLichTB', 'delete'),
                controller: 'lapLichThietBiDeleteCtrl',
                resolve: {
                    targetData: function () {
                        return target;
                    }
                }
            });
            modalInstance.result.then(function (updatedData) {
                $scope.refresh();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };


    }]);
    
    app.controller('lapLichThietBiCreateCtrl', ['$scope', '$uibModalInstance', '$location', 'lapLichThietBiService','dmThietBiService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service,dmThietBiService, configService, $state, tempDataService, $filter, $uibModal, $log, ngNotify) {
            $scope.config = angular.copy(configService);
            $scope.tempData = tempDataService.tempData;
            $scope.target = {};
            $scope.isLoading = false;
            $scope.title = function () { return 'Thêm mới lịch'; };
            $scope.target.timeStart = new Date();
            $scope.lstEquip = [];
            $scope.listDay = angular.copy(tempDataService.tempData('day'));
            $scope.removeInListDay = function(item){
            	item.status = !item.status;
            	console.log(item.status);
            }

            $scope.save = function () {
        		var strDay = "";

//            	$scope.target.chanel = $scope.target.chanel+"";
            	for(var i = 0;i<$scope.listDay.length;i++){
            		if($scope.listDay[i].status){
            			strDay = strDay+$scope.listDay[i].value+",";
            		}
	       			 $scope.target.day = strDay;

            	}
            	var postData = angular.copy($scope.target);
            	var temp = new Date($scope.target.timeStart);
            	postData.timeStart = temp.getHours()+":"+temp.getMinutes()+":00";
            	postData.equipmentID = $scope.target.equipmentID+"";
            	console.log(postData);
            	var myJSON = JSON.stringify(postData);
            	console.log($scope.target);
                service.insertSchedule(postData).then(function (successRes) {
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
            function loadDataPhong (){
            	dmThietBiService.getAllEpuipment({}).then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data.length > 0) {
                        $scope.lstEquip = angular.copy(response.data.data);
                        console.log($scope.data);
                    } else {
                        console.log(response);
                    } 
                }, function (response) {
                    console.log(response);
                });
        	}
            loadDataPhong ();
            
			$scope.displayHepler = function (value) {
				var data = $filter('filter')($scope.lstEquip, {id: value}, true);
				if (data && data.length == 1) {
					return data[0].name + " | "+ data[0].nameRoom;
				} else {
					return '';
				}
			};

            $scope.cancel = function () {
                $uibModalInstance.close(); 
            };
        }]);

    app.controller('lapLichThietBiEditCtrl', ['$scope', '$uibModalInstance', '$location', 'lapLichThietBiService','dmThietBiService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'ngNotify','targetData',
        function ($scope, $uibModalInstance, $location, service,dmThietBiService, configService, $state, tempDataService, $filter, $uibModal, $log, ngNotify,targetData) {
            $scope.config = angular.copy(configService);
            $scope.tempData = tempDataService.tempData;
            $scope.target = targetData;
            $scope.isLoading = false;
            $scope.title = function () { return 'Thêm mới lịch'; };
            $scope.lstEquip = [];
            $scope.listDay = angular.copy(tempDataService.tempData('day'));
            $scope.removeInListDay = function(item){
            	item.status = !item.status;
            	console.log(item.status);
            }
            for(var i =0 ;i<$scope.listDay.length;i++){
            	if($scope.target.day.indexOf($scope.listDay[i].value) >=0){
            		$scope.listDay[i].status = true;
            	}
            }
            $scope.save = function () {
        		var strDay = "";

//            	$scope.target.chanel = $scope.target.chanel+"";
            	for(var i = 0;i<$scope.listDay.length;i++){
            		if($scope.listDay[i].status){
            			strDay = strDay+$scope.listDay[i].value+",";
            		}
	       			 $scope.target.day = strDay;

            	}
            	var postData = angular.copy($scope.target);
            	var temp = new Date($scope.target.timeStart);
            	postData.timeStart = temp.getHours()+":"+temp.getMinutes()+":00";
            	postData.equipmentID = $scope.target.equipmentID+"";
            	console.log(postData);
            	var myJSON = JSON.stringify(postData);
            	console.log($scope.target);
                service.updateSchedule(postData).then(function (successRes) {
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
            function loadDataPhong (){
            	dmThietBiService.getAllEpuipment({}).then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data.length > 0) {
                        $scope.lstEquip = angular.copy(response.data.data);
                        console.log($scope.data);
                    } else {
                        console.log(response);
                    } 
                }, function (response) {
                    console.log(response);
                });
        	}
            loadDataPhong ();
            
			$scope.displayHepler = function (value) {
				var data = $filter('filter')($scope.lstEquip, {id: value}, true);
				if (data && data.length == 1) {
					return data[0].name + " | "+ data[0].nameRoom;
				} else {
					return '';
				}
			};

            $scope.cancel = function () {
                $uibModalInstance.close(); 
            };
        }]);
    
    app.controller('lapLichThietBiDeleteCtrl', ['$scope', '$uibModalInstance', '$location', 'lapLichThietBiService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService, $state, tempDataService, $filter, $uibModal, $log, targetData, ngNotify) {
            $scope.target = targetData;
            $scope.config = angular.copy(configService);
            $scope.targetData = angular.copy(targetData);
            $scope.isLoading = false;

            $scope.title = function () { return 'Bạn có muốn xóa '+$scope.target.name; };

            $scope.save = function () {
                service.deleteSchedule($scope.target).then(function (successRes) {
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
