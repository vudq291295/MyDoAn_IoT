define(['angular'], function (angular) {
    var app = angular.module('authModule', ['configModule']);

	app.service('userService',['localStorageService',function(localStorageService){
		var fac = {};
		fac.CurrentUser = null;
		fac.SetCurrentUser = function (user) {
			fac.CurrentUser = user;
			localStorageService.set('authorizationData', user);
		};
		fac.GetCurrentUser = function () {
		    fac.CurrentUser = localStorageService.get('authorizationData');
			return fac.CurrentUser;
		};
		fac.logout = function() {
		    localStorageService.cookie.clearAll();
		    $state.go('login');
		}
		return fac;
	}]);
	
	app.service('accountService', ['configService', '$http', '$q', 'localStorageService', '$state', 'userService', 
		function (configService, $http, $q, localStorageService, $state, userService) {
		var result={
			getCurrentUser:function(token){
				console.log(token);
			var defer = $q.defer();
				$http({ method: 'get', url: configService.apiServiceBaseUri + "/userinfo", headers: { 'Authorization': 'Bearer '+token } }).then(function (response) {
					console.log(response);
					if(response && response.status===200 && response.data){
						var userStored = response.data.user;
						userStored.access_token = token;
						userService.SetCurrentUser(userStored); 
						$state.go('home');
					}
				}, function (response) {
				});
            return defer.promise;
			},
			login:function(user){
				var obj = {'username':user.username,'password':user.password, 'grant_type': 'password','client_id':'test','client_secret':'test' };
				Object.toparams = function ObjectsToParams(obj) {
                var p = [];
                for (var key in obj) {
                    p.push(key + '=' + encodeURIComponent(obj[key]));
                }
                return p.join('&');
				}
				var defer = $q.defer();
				$http({ method: 'post', url: configService.apiServiceBaseUri + "/oauth/token", data: Object.toparams(obj), headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }).then(function (response) {
					if(response && response.status===200 && response.data){
//						userService.SetCurrentUser(response.data); 
//						$state.go('home');
					}
					defer.resolve(response);
				}, function (response) {
					defer.reject(response);
				});
            return defer.promise;
			},
			logout:function() {
			    localStorageService.cookie.clearAll();
			    $state.go('login');
			}
		};
		return result;
	}]);
	
    app.controller('loginCrtl', ['$scope', '$location', '$http','localStorageService','accountService','$state', function ($scope, $location,$http,localStorageService,accountService,$state) {
        $scope.user = { username: '', password: '', cookie: false, grant_type: 'password' };
        //window.scrollTo(0, 1000);
        //function disableScrolling() {
        //    var x = window.scrollX;
        //    var y = window.scrollY;
        //    window.onscroll = function () { window.scrollTo(x, y); };
        //}
        //disableScrolling()
		$scope.login=function(){
			$scope.msg=null;
			accountService.login($scope.user).then(function(response){
				console.log(response);
				accountService.getCurrentUser(response.data.access_token).then(function(response) {
				},function(response){
					console.log(response);

				    if (response && response.data && response.data.error) {
				        $scope.msg = response.data.error_description;
					}
					$scope.user={username:'',password:'',cookie:false,grant_type:'password'};
					$scope.focusUsername=true;
				});
			},function(response){
				console.log(response);

			    if (response && response.data && response.data.error) {
			        $scope.msg = response.data.error_description;
				}
				$scope.user={username:'',password:'',cookie:false,grant_type:'password'};
				$scope.focusUsername=true;
			});
		};
	}]);

    return app;
});

