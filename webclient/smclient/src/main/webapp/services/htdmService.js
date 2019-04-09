define(['angular'], function (angular) {
    var app = angular.module('htdmModule', []);
    app.service('htdmService', ['configService','$resource',function(configService,$resource) {
        var result = {
            config: configService
        };
        result.buildUrl = function (module, action) {
            return configService.rootUrlWeb + '/_layouts/15/BTS.SP.STC.PHF/views/htdm/' + module + '/' + action + '.html';
        };
        return result;
    }]);

    return app;
});