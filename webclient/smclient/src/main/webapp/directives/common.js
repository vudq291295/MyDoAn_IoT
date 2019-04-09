define(['angular', 'services/configService'], function (angular) {
    var app = angular.module('common-directive', ['configModule']);

    app.directive('preventDefault', function () {
        return function (scope, element, attrs) {
            angular.element(element).bind('click', function (event) {
                event.preventDefault();
                event.stopPropagation();
            });
        }
    });

    //app.directive('dragable', function () {
    //    return {
    //        restrict: 'A',
    //        link: function (scope, elem, attr) {
    //            $(elem).draggable();
    //        }
    //    };
    //});

    app.directive('myLoading', function () {
        var result = {
            restrict: 'E',
            scope: {
                show: '=',
                cls: '='
            },
            replace: true,
            template: '<div style="display:{{data}}"><i class="fa fa-spinner fa-spin" style="font-size:50px"></i></div>',
            link: function (scope, element, attrs, ngModel) {
                if (scope.show) {
                    scope.data = 'block';
                } else {
                    scope.data = 'none';
                }
            }
        }
        return result;
    });

    app.directive('ngEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.ngEnter);
                    });

                    event.preventDefault();
                    element.next().focus();
                }
            });
        };
    });

    app.directive('pwCheck', [function () {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ctrl) {
                var firstPassword = '#' + attrs.pwCheck;
                elem.add(firstPassword).on('keyup', function () {
                    scope.$apply(function () {
                        var v = elem.val() === $(firstPassword).val();
                        ctrl.$setValidity('pwmatch', v);
                    });
                });
            }
        }
    }]);

    app.directive('report', ['configService', function (configService) {
        return {
            restrict: 'EA',
            transclude: 'true',
            scope: {
                name: '@',
                params: '@'
            },
            template: "",
            link: function (scope, element, attrs) {
                //create the viewer object first, can be done in index.html as well
                var reportViewer = $("#reportViewer1").data("telerik_ReportViewer");
                if (!reportViewer) {
                    $("#reportViewer1").toggle();
                    var objpara = JSON.parse(scope.params);
                    $(document).ready(function () {
                        $("#reportViewer1").telerik_ReportViewer({
                            error: function (e, args) {
                                alert('Error from report directive:' + args);
                            },
                            reportSource: {
                                report: scope.name,
                                parameters: objpara
                            },
                            serviceUrl: configService.rootUrlWebApi + "/reports",
                            scaleMode: 'SPECIFIC',
                            scale: 1.0,
                            viewMode: 'PRINT_PREVIEW'
                        });
                    });
                }
                scope.$watch('name', function () {
                    var reportViewer = $("#reportViewer1").data("telerik_ReportViewer");
                    if (reportViewer) {
                        var rs = reportViewer.reportSource();
                        if (rs && rs.report) {
                            if (rs.report !== scope.name &&
                                rs.parameters !== scope.parameters) {
                                reportViewer.reportSource({
                                    report: scope.name,
                                    parameters: angular.toJson(scope.parameters),
                                });
                            }
                        }
                    }
                });
            }
        };
    }]);

    return app;
});