define(['angular', 'controllers/auth/AuController'], function () {
    var app = angular.module('InterceptorModule', ['authModule']);
    app.factory('interceptorService', ['$q', '$injector', '$location', '$log', 'userService', '$state',  
    	function ($q, $injector, $location, $log, userService, $state) {
        var interceptorServiceFactory = {};
        var _request = function (request) {
            request.headers = request.headers || {};
            var currentUser = userService.GetCurrentUser();
            if (currentUser != null) {
                request.headers.Authorization = 'Bearer ' + currentUser.access_token;
            }
            return request;
        }

        var _response = function (res) {

            return res;
        }
        var _requestError = function (request) {

            return request
        }
        var _responseError = function (rejection) {
            if (rejection.status === 401) {
                console.log('AccessDenied :', rejection);
                userService.logout();
//                $state.go('login');
            } else if (rejection.status === 500) {
                //ngNotify.set("Lỗi hệ thống.", { duration: 3000, type: 'error' });
            } else if (rejection.status === 400) {
                // ngNotify.set("Dữ liệu đầu vào không hợp lệ.", { duration: 3000, type: 'error' });
            } else {

            }

            return $q.reject(rejection);
        }
        interceptorServiceFactory.request = _request;
        interceptorServiceFactory.response = _response;
        interceptorServiceFactory.requestError = _requestError;
        interceptorServiceFactory.responseError = _responseError;

        return interceptorServiceFactory;
    }]);
    return app;
});