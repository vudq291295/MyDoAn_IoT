require.config({
    base: '/',
    paths: {
        'jquery': 'utils/kendo/js/jquery.min',
        'jquery-ui': 'utils/jquery-ui/jquery-ui.min',
        'bootStrapBundel' : 'vendor/bootstrap/js/bootstrap.bundle.min',
        'jqueryEasey' :'vendor/jquery-easing/jquery.easing.min',
        'sbAdmin2' : 'js/sb-admin-2.min',
        'chartSbAdmin2' : 'vendor/chart.js/Chart.min',
/*        'bootstrap': 'lib/bootstrap.min',
*/        'angular': 'utils/kendo/js/angular.min',
        'angular-animate': 'lib/angular-animate.min',
        'angular-resource': 'lib/angular-resource.min',
        'angular-sanitize': 'lib/angular-sanitize.min',
        'angular-filter': 'lib/angular-filter.min',
        'angular-cache': 'lib/angular-cache.min',
        'ocLazyLoad': 'lib/ocLazyLoad.require',
        'uiRouter': 'lib/angular-ui-router.min',
        'angularStorage': 'lib/angular-local-storage.min',
        'ui-bootstrap': 'lib/ui-bootstrap-tpls-1.3.3',
        'loading-bar': 'utils/loading-bar/loading-bar.min',
        'toaster': 'utils/toaster/toaster.min',
        'ng-file-upload': 'utils/ng-file-upload-all.min',
        'dynamic-number': 'utils/dynamic-number.min',
        'angular-confirm': 'utils/angular-confirm/angular-confirm.min',
        'ngNotify': 'lib/ng-notify/ng-notify.min',
        'moment': 'lib/moment',
        'angularPaho': 'lib/mqtt/angular-paho',
        'moment': 'lib/moment',
        'chart-js': 'lib/Chart.min',
        'angular-chart': 'lib/angular-chart.min'

//        'browserMqtt': 'lib/mqtt/browserMqtt'

    },
    shim: {
        'jquery': {
            exports: '$'
        },
        'bootStrapBundel': ['jquery'],
        'jqueryEasey': ['jquery'],
        'sbAdmin2': ['jquery'],
        'chartSbAdmin2': ['jquery'],
        'jquery-ui': ['jquery'],
/*        'bootstrap': ['jquery'],
*/        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'ocLazyLoad': ['angular'],
        'uiRouter': ['angular'],
        'angular-animate': ['angular'],
        'angular-resource': ['angular'],
        'angular-filter': ['angular'],
        'angular-cache': ['angular'],
        'angular-sanitize': ['angular'],
        'angularStorage': ['angular'],
        'ui-bootstrap': ['angular'],
        'loading-bar': ['angular'],
        'toaster': ['angular'],
        'ng-file-upload': ['angular'],
        'dynamic-number': ['angular'],
        'angular-confirm': ['angular'],
        'ngNotify': ['angular'],
        'moment': ['angular'],
        'angularPaho': ['angular'],
        'chart-js': ['angular'],
        'angular-chart': ['chart-js']

//        'browserMqtt': ['angular'],
    },
    waitSeconds: 0
});
// Start the main app logic.
require(['app'], function () {
    angular.bootstrap(document.body, ['myApp']);
});
