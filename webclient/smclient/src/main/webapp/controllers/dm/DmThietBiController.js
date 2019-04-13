define(['angular','ui-bootstrap', 'controllers/dm/DmPhongController'], function (angular) {
    'use strict';
    var app = angular.module('dmThietBiModule', ['ui.bootstrap','dmPhongModule']);
    app.factory('dmThietBiService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/equipment';
        var result = {
    		getAllEpuipment: function () {
                return $http.get(serviceUrl + '/getAllEpuipment');
            },
            getEpuipmentByRoom: function (data) {
                return $http.get(serviceUrl + '/getEpuipmentByRoom/'+data);
            },
            insertEpuipment : function (data) {
                return $http.post(serviceUrl + '/insertEpuipment',data);
            },
            updateEpuipment : function (data) {
                return $http.post(serviceUrl + '/updateEpuipment',data);
            },
            deleteEpuipment : function (data) {
                return $http.post(serviceUrl + '/deleteEpuipment',data);
            },
        }
        return result;
    }]);
    
    app.controller('dmThietBiViewCtrl', ['$scope','dmThietBiService','configService','dmPhongService','$uibModal',
    	function ($scope,service,configService,dmPhongService,$uibModal) {
        $scope.config = {
                label: angular.copy(configService.label)
        }

    	function filterData() {
    		service.getAllEpuipment().then(function (response) {
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
        	var a = configService.buildUrl('danhmuc/dmThietBi', 'add');
            var modalInstance = $uibModal.open({
                backdrop: 'static',
                size: 'lg',
                windowClass : 'show app-modal-window',
            	templateUrl: a ,
                controller: 'dmThietBiCreateCtrl',
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
                templateUrl: configService.buildUrl('danhmuc/dmThietBi', 'edit'),
                controller: 'dmThietBiEditCtrl',
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
                templateUrl: configService.buildUrl('danhmuc/dmThietBi', 'detail'),
                controller: 'dmThietBiDetailCtrl',
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
                templateUrl: configService.buildUrl('danhmuc/dmThietBi', 'delete'),
                controller: 'dmThietBiDeleteCtrl',
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
    
    app.controller('dmThietBiCreateCtrl', ['$scope', '$uibModalInstance', '$location', 'dmThietBiService', 'configService','dmPhongService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService,dmPhongService, $state, tempDataService, $filter, $uibModal, $log, ngNotify) {
            $scope.config = angular.copy(configService);
            $scope.tempData = tempDataService.tempData;
            $scope.target = {};
            $scope.isLoading = false;
            $scope.title = function () { return 'Thêm mới phòng'; };
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
            $scope.save = function () {
//            	$scope.target.chanel = $scope.target.chanel+"";
            	var myJSON = JSON.stringify($scope.target);
                service.insertEpuipment($scope.target).then(function (successRes) {
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

    app.controller('dmThietBiEditCtrl', ['$scope', '$uibModalInstance', '$location', 'dmThietBiService', 'configService','dmPhongService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService,dmPhongService, $state, tempDataService, $filter, $uibModal, $log, targetData, ngNotify) {
            $scope.target = targetData;
            $scope.tempData = tempDataService.tempData;
            $scope.config = angular.copy(configService);
            $scope.targetData = angular.copy(targetData);
            $scope.isLoading = false;

            $scope.title = function () { return 'Cập nhật phòng'; };
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

            $scope.save = function () {
            	var postData = {};
            	postData.id = $scope.target.id;
            	postData.roomId = $scope.target.roomId;
            	postData.name= $scope.target.name;
            	postData.status= $scope.target.status;
            	postData.portOutput= $scope.target.portOutput;

                service.updateEpuipment(postData).then(function (successRes) {
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

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);
    app.controller('dmThietBiDetailCtrl', ['$scope', '$uibModalInstance', '$location', 'dmThietBiService', 'configService','dmPhongService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData',
	function ($scope, $uibModalInstance, $location, service, configService,dmPhongService, $state, tempDataService, $filter, $uibModal, $log, targetData) {
	    $scope.tempData = tempDataService.tempData;
	    $scope.config = tempDataService.config;
	    $scope.target = targetData;
	    $scope.title = function () { return 'Chi tiết phòng'; };
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

	    $scope.cancel = function () {
	        $uibModalInstance.dismiss('cancel');
	    };

	}]);
    app.controller('dmThietBiDeleteCtrl', ['$scope', '$uibModalInstance', '$location', 'dmThietBiService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify',
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
                service.deleteEpuipment(postData).then(function (successRes) {
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

