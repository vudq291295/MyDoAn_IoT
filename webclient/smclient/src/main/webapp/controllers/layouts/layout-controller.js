// 'use strict';
define(['angular'
], function (angular) {
    var app = angular.module('layoutModule', []);
    app.controller('layoutCrtl', ['$scope', 'blockModalService', function ($scope, blockModalService) {
        $scope.blockModalService = blockModalService;
    }]);
    return app;
});

