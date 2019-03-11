/**
 * 
 */
auModule.factory('userService',['$resource','$http','configService',
	function($resource,$http,configService){
	var serviceUrl = configService.rootWebApiUrl+'/usersServiceRest';
	var result = {
		getUser:function(callback){
			$http.get(serviceUrl+'/getAllUser').success(callback);
		},	
		getUserByUP:function(data,callback){
			$http.post(serviceUrl+'/getUserByUP',data).success(callback);
		}	

	};
	return result;
}]);

auModule.factory('userService2', ['$rootScope', function ( $rootScope) {

    var fac = {};
    fac.CurrentUser = null;

    fac.SetCurrentUser = function (user) {
        fac.CurrentUser = user;
//        localStorageService.set('authorizationData', angular.toJson(user));
        $rootScope.currentUser = user;
    }

    fac.GetCurrentUser = function () {
//        fac.CurrentUser = angular.fromJson(localStorageService.get('authorizationData'));
        $rootScope.currentUser = fac.CurrentUser;
        return fac.CurrentUser;
    }
    return fac;
}]);



auModule.controller('userController',['$scope','$state','homeService','md5','userService2','$rootScope','$location',
	function($scope,$state,service,md5,userService2,$rootScope,$location){
		$scope.loginData = {};
		service.getUser(function(resopnse){
			console.log(resopnse);
			
		});
		$scope.login = function(){
			$scope.loginData.passWord = md5.createHash($scope.password);
			service.getUserByUP($scope.loginData,function(resopnse){
				console.log(resopnse);

				if(resopnse.success){
					userService2.SetCurrentUser(resopnse.data);
					alert("Đăng nhập thành công");
					$state.go("home");
					console.log($rootScope.currentUser);
				}
				else{
					alert(resopnse.message);
				}
				
			});
			console.log($scope.loginData);
		};
		console.log('vao r');
}]);