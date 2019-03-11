var mainApp = angular.module('myApp',['ngResource','ui.router','ngRoute','ngServices','angular-md5',
	'auModule']);

mainApp.config([
	'$windowProvider','$stateProvider','$httpProvider', '$urlRouterProvider', '$routeProvider', '$locationProvider',
	function($windowProvider,$stateProvider, $httpProvider, $urlRouterProvider, $routeProvider, $locationProvider){
		var window = $windowProvider.$get('$window');
        var hostname = window.location.hostname;
        var port = window.location.port;
        var rootUrl = 'http://' + hostname + ':' + port+'/vudq-web/';
        console.log(rootUrl);
        $urlRouterProvider.otherwise('login');
        $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: rootUrl + 'app/views/au/home.html',
            controller: 'homeController'
        })
        .state('login', {
            url: '/login',
            templateUrl: rootUrl + 'app/views/au/login.html',
            controller: 'userController'
        })
       ;
   // $httpProvider.interceptors.push('httpInterceptor');
}]);