/**
 * 
 */
ngServices.factory('configService',['$resource','$http','$window','$injector',
	function($resource,$http,$window,$injector){
		var hostname = window.location.hostname;
		var port = window.location.port;
		var rootUrl = 'http://'+hostname+':'+port;
		var rootUrlApi = 'http://localhost:8080/vudq-service';
		
		var result ={
			rootWebApiUrl:rootUrlApi,
		}
		
		return result;
}]);