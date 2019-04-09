define(['angular', 'services/configService'], function (angular) {
    var app = angular.module('sysModule', ['configModule']);

    app.service('sysService', ['configService', '$window', '$resource', '$http', function (configService, $window, $resource, $http) {
        var result = {
            config: configService
        };

        result.buildUrl = function (module, action) {
            return configService.rootUrlWeb + '/_layouts/15/BTS.SP.STC.PHF/views/sys/' + module + '/' + action + '.html';
        };

        //var tempData = $window.CacheManager();

        //result.cacheStatus = tempData.cacheStatus;
        //result.tempData = tempData;

        //function initData() {
        //    tempData.register('status',
        //        [
        //            { Value: 'A', Text: 'Sử dụng', Id: 1 },
        //            { Value: 'I', Text: 'Không sử dụng', Id: 2 }
        //        ],
        //        null);

        //    tempData.register('dmCHUONGs', null, function () {
        //        return $resource(configService.rootUrlWebApi + '/Dm/DM_CHUONG/GetSelectData').query({}, isArray = true);
        //    });
        //}

        //initData();
        return result;
    }]);

    return app;
});