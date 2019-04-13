define(['angular'], function (angular) {
    var app = angular.module('leftModule', []);

    app.controller('leftCtrl', ['$scope','userService','accountService',function ($scope,userService,accountService) {
    	console.log('aaaaaa');
    	$scope.currentUser = userService.GetCurrentUser();
        $scope.logOut = function () {
            accountService.logout();
        };

    }]);
    return app;
});

