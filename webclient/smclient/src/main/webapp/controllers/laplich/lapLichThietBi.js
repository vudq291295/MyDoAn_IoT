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
                templateUrl: configService.buildUrl('danhmuc/lapLichThietBi', 'delete'),
                controller: 'lapLichThietBiDeleteCtrl',
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
    
    app.controller('lapLichThietBiCreateCtrl', ['$scope', '$uibModalInstance', '$location', 'lapLichThietBiService','dmThietBiService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service,dmThietBiService, configService, $state, tempDataService, $filter, $uibModal, $log, ngNotify) {
            $scope.config = angular.copy(configService);
            $scope.tempData = tempDataService.tempData;
            $scope.target = {};
            $scope.isLoading = false;
            $scope.title = function () { return 'Thêm mới lịch'; };
            $scope.target.timeStart = new Date();
            $scope.save = function () {
//            	$scope.target.chanel = $scope.target.chanel+"";
            	var myJSON = JSON.stringify($scope.target);
                service.insertRoom($scope.target).then(function (successRes) {
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
                        $scope.data = angular.copy(response.data.data);
                        console.log($scope.data);
                    } else {
                        console.log(response);
                    } 
                }, function (response) {
                    console.log(response);
                });
        	}
            loadDataPhong ();

            $scope.cancel = function () {
                $uibModalInstance.close(); 
            };
        }]);

    return app;
});
