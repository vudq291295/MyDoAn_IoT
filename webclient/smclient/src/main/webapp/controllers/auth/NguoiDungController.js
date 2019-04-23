define(['angular', 'controllers/auth/donViController'], function (angular) {
    var app = angular.module('nguoiDungModule', ['donViModule']);
    app.factory('nguoiDungService', ['$http', 'configService', function ($http, configService) {
        var serviceUrl = configService.rootUrlWebApi + '/user';
        var result = {
    		getAllUser: function (data) {
                return $http.post(serviceUrl + '/getAllUser',data);
            },
            insertUser : function (data) {
                return $http.post(serviceUrl + '/insertUser',data);
            },
            updateUser : function (data) {
                return $http.post(serviceUrl + '/updateUser',data);
            },
            deleteUser : function (data) {
                return $http.post(serviceUrl + '/deleteUser',data);
            },
        }
        return result;
    }]);
    
    app.controller('nguoiDungViewCtrl', ['$scope','$uibModal','nguoiDungService','configService','$uibModal','tempDataService','$filter','donViService',
    	function ($scope,$uibModal,service,configService,$uibModal,tempDataService,$filter,donViService) {
    	$scope.lstLoaiTaiKhoan = tempDataService.tempData('loaiTaiKhoan');
    	$scope.lstGioiTinh = tempDataService.tempData('loaiTaiKhoan');
        $scope.config = {
                label: angular.copy(configService.label)
        }
        $scope.search = {};
        $scope.lstDonVi = [];
        
    	function loadDonVi() {
    		donViService.getAllUnit({}).then(function (response) {
            	console.log(response);
                if (response && response.data && response.data.data.length > 0) {
                    $scope.lstDonVi = angular.copy(response.data.data);
            		filterData();
                } else {
                    console.log(response);
                }
            }, function (response) {
                console.log(response);
            });
		};
		loadDonVi();

    	function filterData() {
    		service.getAllUser($scope.search).then(function (response) {
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
		
		$scope.displayHeplerLTK = function (value) {
			var data = $filter('filter')($scope.lstLoaiTaiKhoan, {Value: value}, true);
			if (data && data.length == 1) {
				return data[0].Text;
			} else {
				return '';
			}
		};

		$scope.displayHeplerDonVi = function (value) {
			var data = $filter('filter')($scope.lstDonVi, {unitCode: value}, true);
			if (data && data.length == 1) {
				return data[0].name;
			} else {
				return '';
			}
		};

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
                templateUrl: configService.buildUrl('auth/nguoiDung', 'add'),
                controller: 'nguoiDungCreateCtrl',
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
                templateUrl: configService.buildUrl('auth/nguoiDung', 'edit'),
                controller: 'nguoiDungEditCtrl',
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
                templateUrl: configService.buildUrl('auth/nguoiDung', 'detail'),
                controller: 'nguoiDungDetailCtrl',
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
                templateUrl: configService.buildUrl('auth/nguoiDung', 'delete'),
                controller: 'nguoiDungDeleteCtrl',
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
    
    app.controller('nguoiDungCreateCtrl', ['$scope', '$uibModalInstance', '$location', 'nguoiDungService', 'configService', '$state',
    	'tempDataService', '$filter', '$uibModal', '$log', 'ngNotify','donViService',
        function ($scope, $uibModalInstance, $location, service, configService, $state, tempDataService, $filter, $uibModal, $log, ngNotify,donViService) {
            $scope.config = angular.copy(configService);
            $scope.tempData = tempDataService.tempData;
            $scope.target = {};
            $scope.isLoading = false;
            $scope.title = function () { return 'Thêm mới tài khoản'; };
            $scope.lstDonVi = {};
        	$scope.lstLoaiTaiKhoan = tempDataService.tempData('loaiTaiKhoan');
        	$scope.lstGioiTinh = tempDataService.tempData('loaiTaiKhoan');

        	function loadDonVi() {
        		donViService.getAllUnit({}).then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data.length > 0) {
                        $scope.lstDonVi = angular.copy(response.data.data);
                    } else {
                        console.log(response);
                    }
                }, function (response) {
                    console.log(response);
                });
    		};
    		loadDonVi();

    		$scope.displayHeplerDonVi = function (value) {
    			var data = $filter('filter')($scope.lstDonVi, {unitCode: value}, true);
    			if (data && data.length == 1) {
    				return data[0].name;
    			} else {
    				return '';
    			}
    		};
    		
    		$scope.changeLoaiTK = function (){
    			if($scope.target.type == 1){
    				$scope.target.unitCode = "HT";
    			}
    		}

            $scope.save = function () {
//            	$scope.target.chanel = $scope.target.chanel+"";
            	var myJSON = JSON.stringify($scope.target);
                service.insertUser($scope.target).then(function (successRes) {
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

    app.controller('nguoiDungEditCtrl', ['$scope', '$uibModalInstance', '$location', 'nguoiDungService', 'configService', '$state', 
    	'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify','donViService',
        function ($scope, $uibModalInstance, $location, service, configService, $state, tempDataService, $filter, $uibModal, $log, targetData, ngNotify,donViService) {
            $scope.target = targetData;
            $scope.tempData = tempDataService.tempData;
            $scope.config = angular.copy(configService);
            $scope.targetData = angular.copy(targetData);
            $scope.isLoading = false;

            $scope.title = function () { return 'Cập nhật tài khoản'; };
            $scope.lstDonVi = {};
        	$scope.lstLoaiTaiKhoan = tempDataService.tempData('loaiTaiKhoan');
        	$scope.lstGioiTinh = tempDataService.tempData('loaiTaiKhoan');

        	function loadDonVi() {
        		donViService.getAllUnit({}).then(function (response) {
                	console.log(response);
                    if (response && response.data && response.data.data.length > 0) {
                        $scope.lstDonVi = angular.copy(response.data.data);
                    } else {
                        console.log(response);
                    }
                }, function (response) {
                    console.log(response);
                });
    		};
    		loadDonVi();

            $scope.save = function () {
                service.updateUser($scope.target).then(function (successRes) {
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
    app.controller('nguoiDungDetailCtrl', ['$scope', '$uibModalInstance', '$location', 'nguoiDungService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData',
	function ($scope, $uibModalInstance, $location, service, configService, $state, tempDataService, $filter, $uibModal, $log, targetData) {
	    $scope.tempData = tempDataService.tempData;
	    $scope.config = tempDataService.config;
	    $scope.target = targetData;
	    $scope.title = function () { return 'Chi tiết tài khoản'; };

	    $scope.cancel = function () {
	        $uibModalInstance.dismiss('cancel');
	    };

	}]);
    app.controller('nguoiDungDeleteCtrl', ['$scope', '$uibModalInstance', '$location', 'nguoiDungService', 'configService', '$state', 'tempDataService', '$filter', '$uibModal', '$log', 'targetData', 'ngNotify',
        function ($scope, $uibModalInstance, $location, service, configService, $state, tempDataService, $filter, $uibModal, $log, targetData, ngNotify) {
            $scope.target = targetData;
            $scope.config = angular.copy(configService);
            $scope.targetData = angular.copy(targetData);
            $scope.isLoading = false;

            $scope.title = function () { return 'Bạn có muốn xóa '+$scope.target.fullName; };

            $scope.save = function () {
                service.deleteUser($scope.target).then(function (successRes) {
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

