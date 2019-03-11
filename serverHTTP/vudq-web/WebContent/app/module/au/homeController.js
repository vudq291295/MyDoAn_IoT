/**
 * 
 */
auModule.factory('homeService',['$resource','$http','configService',
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
auModule.controller('homeController',['$scope','homeService','md5','$rootScope',
	function($scope,service,md5,$rootScope){
		$scope.username = $rootScope.currentUser.userName;
	console.log('vao r');
}]);