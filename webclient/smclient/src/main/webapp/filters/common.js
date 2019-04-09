define(['angular', 'angular-sanitize'], function (angular) {
    var app = angular.module('common-filter', ['ngSanitize']);
    app.filter('displayBool', function () {
        return function (input) {
            if (input == 1) {
                return "<i class='glyphicon glyphicon-ok text-success'></i>";
            }
        }
    });

    app.filter('status', function () {
        return function (input) {
            if (input == 'A') {
                return "Sử dụng";
            }
            return "Không sử dụng";
        }
    });

    app.filter('isNegative', function () {
        return function (input) {
            var ip = angular.copy(input);
            ip = ip.toString();
            if (ip < 0 || ip.indexOf('-') !== -1) {
                ip = ip.replace('-', '');
                return "<span style='color:red;'>(" + ip + ")</span>";
            }
            return input;
        }
    });

    app.filter('trustHtml', [
        '$sce',
        function ($sce) {
            return function (text) {
                return $sce.trustAsHtml(text + "");
            };
        }
    ]);

    app.filter('startswith', function () {
        return function (input, alpha, property) {
            var _out = [];
            if (angular.isUndefined(alpha)) {
                _out = input;
            }
            angular.forEach(input, function (item) {
                if (item[property].startsWith(alpha)) {
                    _out.push(item);
                }
            });
            return _out;
        };
    });

    app.filter('dateFormat', function () {
        return function (date) {
            if (date) {
                try {
                    var fullDate = new Date(parseInt(date.substr(6)));
                    var twoDigitMonth = (fullDate.getMonth() + 1) + ""; if (twoDigitMonth.length == 1) twoDigitMonth = "0" + twoDigitMonth;
                    var twoDigitDate = fullDate.getDate() + ""; if (twoDigitDate.length == 1) twoDigitDate = "0" + twoDigitDate;
                    var currentDate = twoDigitDate + "/" + twoDigitMonth + "/" + fullDate.getFullYear();
                    return currentDate;
                } catch (error) {
                    return date;
                }
            }
            return date;
        };
    });

    return app;
});