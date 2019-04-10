/**
 * loads sub modules and wraps them up into the main module
 * this should be used for top-level module definitions only
 */
define([
    'jquery',
    'jquery-ui',
    'angular',
    'states/auth',
    'states/danhmuc',
    'config/config',
    'ocLazyLoad',
    'uiRouter',
    'angularStorage',
    'angular-animate',
    'angular-resource',
    'angular-sanitize',
    'angular-filter',
    'angular-cache',
    'ui-bootstrap',
    'loading-bar',
    'toaster',
    'ng-file-upload',
    'dynamic-number',
    'angular-confirm',
    'services/interceptorService',
    'services/configService',
    'services/tempDataService',
    'filters/common',
    'directives/common',
    'ngNotify',
    'moment',
    'angularPaho'
], function (jquery, jqueryui, angular,state_auth,state_danhmuc) {
    'use strict';
    var app = angular.module('myApp', ['oc.lazyLoad', 'ui.router', 'InterceptorModule', 'LocalStorageModule', 'ui.bootstrap', 'configModule', 'tempDataModule',
        'angular-loading-bar', 'ngAnimate', 'common-filter', 'common-directive', 'ngResource', 'angular.filter', 'angular-cache', 'toaster',
        'ngFileUpload', 'ngSanitize', 'dynamicNumber', 'cp.ngConfirm', 'angularPaho','ngNotify']);
    app.service('securityService', ['$http', 'configService', function ($http, configService) {
        var result = {
            getAccessList: function (mcn) {
                return $http.get(configService.rootUrlWebApi + '/Shared/GetAccesslist/' + mcn);
            }
        };
        return result;
    }]);
    app.directive('enter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    event.preventDefault();
                    var fields = $(this).parents('form:eq(0),body').find('input, textarea, select, button');
                    var index = fields.index(this);
                    if (index > -1 && (index + 1) < fields.length)
                        fields.eq(index + 1).focus();
                    fields.eq(index + 1).select();
                }
            });
        };
    });
    app.service('blockModalService', function () {
        var result = this;
        result.isBlocked = false;
        result.setValue = function (value) {
            if (result.isBlocked !== value) {
                result.isBlocked = value;
            }
        };
        return result;
    });

    app.config(['CacheFactoryProvider', function (CacheFactoryProvider) {
        angular.extend(CacheFactoryProvider.defaults, {
            maxAge: 3600000, //1 hour
            deleteOnExpire: 'aggressive',
            storageMode: 'memory',
            onExpire: function (key, value) {
                var _this = this;
                if (key.indexOf('/') !== -1) {
                    angular.injector(['ng']).get('$http').get("/dm/" + key).then(function (successRes) {
                        console.log('successResCache', successRes);
                        _this.put(key, successRes.data);
                    }, function (errorRes) {
                        console.log('errorRes', errorRes);
                    });
                } else {
                    _this.put(key, value);
                }
            }
        });
    }]);

    //validate number
    app.config(['dynamicNumberStrategyProvider', function (dynamicNumberStrategyProvider) {
        dynamicNumberStrategyProvider.addStrategy('number', {
            numInt: 18,
            numFract: 2,
            numSep: ',',
            numPos: true,
            numNeg: true,
            numRound: 'round',
            numThousand: true,
            numThousandSep: ' '
        });
        dynamicNumberStrategyProvider.addStrategy('number-utc', {
            numInt: 18,
            numFract: 2,
            numSep: '.',
            numPos: true,
            numNeg: true,
            numRound: 'round',
            numThousand: true
        });
        dynamicNumberStrategyProvider.addStrategy('number-int', {
            numInt: 18,
            numFract: 0,
            numSep: ',',
            numPos: true,
            numNeg: true,
            numRound: 'ceil',
            numThousand: true
        });
        dynamicNumberStrategyProvider.addStrategy('number-38', {
            numInt: 21,
            numFract: 2,
            numSep: ',',
            numPos: true,
            numNeg: true,
            numRound: 'round',
            numThousand: true
        });
        dynamicNumberStrategyProvider.addStrategy('number-int-38', {
            numInt: 21,
            numFract: 0,
            numSep: ',',
            numPos: true,
            numNeg: true,
            numRound: 'ceil',
            numThousand: true
        });
    }]);

    app.config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', 'localStorageServiceProvider', 'cfpLoadingBarProvider', '$httpProvider',
        function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, localStorageServiceProvider, cfpLoadingBarProvider, $httpProvider) {
            $httpProvider.interceptors.push('interceptorService');
            cfpLoadingBarProvider.includeSpinner = true;
            cfpLoadingBarProvider.includeBar = true;
            localStorageServiceProvider.setStorageType('cookie').setPrefix('IOT');
            $ocLazyLoadProvider.config({
                jsLoader: requirejs,
                loadedModules: ['app'],
                asyncLoader: require
            });
            var layoutUrl = "/smclient/";
            $urlRouterProvider.otherwise("/home");

            $stateProvider.state('root', {
                abstract: true,
                views: {
                    'viewRoot': {
                        templateUrl: layoutUrl + "views/layouts/layout.html",
                        controller: "layoutCrtl as ctrl"

                    }
                },
                resolve: {
                    loadModule: ['$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                        var deferred = $q.defer();
                        require(['controllers/layouts/layout-controller'],
                            function (layoutModule) {
                                deferred.resolve();
                                $ocLazyLoad.inject(layoutModule.name);
                            });
                        return deferred.promise;
                    }]
                }
            });

            $stateProvider.state('login', {
                url: "/login",
                abstract: false,
                views: {
                    'viewRoot': {
                        templateUrl: layoutUrl + "views/layouts/login.html",
                        controller: "loginCrtl as ctrl"

                    }
                },
                resolve: {
                    loadModule: ['$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                        var deferred = $q.defer();
                        require(['controllers/auth/AuController'],
                            function (layoutModule) {
                                deferred.resolve();
                                $ocLazyLoad.inject(layoutModule.name);
                            });
                        return deferred.promise;
                    }]
                }
            });

            $stateProvider.state('layout', {
                parent: 'root',
                abstract: true,
                views: {
                    'viewHeader': {
                        templateUrl: layoutUrl + "views/layouts/header.html",
                        controller: "HeaderCtrl as ctrl"
                    },
                    'viewFooter': {
                        templateUrl: layoutUrl + "views/layouts/footer.html"
                    }

                },
                resolve: {
                    loadModule: [
                        '$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                            var deferred = $q.defer();
                            require(['controllers/layouts/header-controller'],
                                function (headerModule) {
                                    deferred.resolve();
                                    $ocLazyLoad.inject(headerModule.name);
                                });
                            return deferred.promise;
                        }
                    ]

                }
            });

            $stateProvider.state('home', {
                url: "/home",
                parent: 'layout',
                abstract: false,
                views: {
                    'viewMain@root': {
                        templateUrl: layoutUrl + "views/layouts/home.html",
                        controller: "homeCtrl as ctrl"
                    }
                },
                resolve: {
                    loadModule: ['$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                        var deferred = $q.defer();
                        require(['controllers/layouts/home-controller'],
                            function (homeModule) {
                                deferred.resolve();
                                $ocLazyLoad.inject(homeModule.name);
                            });
                        return deferred.promise;
                    }]
                }
            });

            $stateProvider.state('AccessDenied ', {
                url: "/AccessDenied",
                parent: 'layout',
                abstract: false,
                views: {
                    'viewMain@root': {
                        templateUrl: layoutUrl + "views/layouts/401.html"
                    }
                }
            });

            var lststate = [];
            lststate = lststate.concat(state_auth).concat(state_danhmuc);
            angular.forEach(lststate, function (state) {
                $stateProvider.state(state.name, {
                    url: state.url,
                    parent: state.parent,
                    abstract: state.abstract,
                    views: state.views,
                    resolve: {
                        loadModule: ['$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                            var deferred = $q.defer();
                            require([state.moduleUrl], function (module) {
                                deferred.resolve();
                                $ocLazyLoad.inject(module.name);
                            });
                            return deferred.promise;
                        }]
                    }
                });
            });
        }]);

    return app;
});
