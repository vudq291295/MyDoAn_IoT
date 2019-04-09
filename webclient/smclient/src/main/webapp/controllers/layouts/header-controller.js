define(['angular', 'controllers/auth/AuController', 'controllers/dm/MenuController'], function (angular) {
    var app = angular.module('headerModule', ['authModule', 'configModule','menuModule']);
    var layoutUrl = "/smclient/";
    app.directive('tree', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                tree: '='
            },
            templateUrl: layoutUrl + 'utils/tree/template-ul.html'
        };
    });
    app.directive('leaf', function ($compile) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                leaf: "="
            },
            templateUrl: layoutUrl + 'utils/tree/template-li.html',
            link: function (scope, element, attrs) {
                if (angular.isArray(scope.leaf.Children) && scope.leaf.Children.length > 0) {
                    element.append("<tree tree='leaf.Children'></tree>");
                    element.addClass('dropdown-submenu');
                    $compile(element.contents())(scope);
                } else {
                }
            }
        };
    });
    app.controller('HeaderCtrl', ['$scope', 'configService', '$state', 'accountService', '$log', 'userService','menuService',
    function ($scope, configService, $state, accountService, $log, userService,menuService) {
        function treeify(list, idAttr, parentAttr, childrenAttr) {
            if (!idAttr) idAttr = 'maMenu';
            if (!parentAttr) parentAttr = 'maCha';
            if (!childrenAttr) childrenAttr = 'Children';
            var lookup = {};
            var result = {};
            result[childrenAttr] = [];
            list.forEach(function (obj) {
                lookup[obj[idAttr]] = obj;
                obj[childrenAttr] = [];
            });
            list.forEach(function (obj) {
                if (obj[parentAttr] != null) {
                    lookup[obj[parentAttr]][childrenAttr].push(obj);
                } else {
                    result[childrenAttr].push(obj);
                }
            });
            return result;
        };
//
        function loadUser() {
            $scope.currentUser = userService.GetCurrentUser();
            if (!$scope.currentUser) {
                $state.go('login');
            }
            menuService.getAllMenu().then(function (response) {
            	console.log(response);
                if (response && response.data && response.data.data.length > 0) {
                    $scope.lstMenu = angular.copy(response.data.data);
                    $scope.treeMenu = treeify($scope.lstMenu);
                    console.log($scope.treeMenu);
                } else {
                    console.log(response);
                }
            }, function (response) {
                console.log(response);
            });

        }
        loadUser();
//
        $scope.logOut = function () {
            accountService.logout();
        };
    }]);
    return app;
});