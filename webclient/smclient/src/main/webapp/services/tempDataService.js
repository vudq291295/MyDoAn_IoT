define(['angular'], function () {
    var app = angular.module('tempDataModule', []);

    app.factory('tempDataService', ['CacheFactory', function (CacheFactory) {
        var dataCache;
        if (!CacheFactory.get('dataCache')) {
            dataCache = CacheFactory('dataCache');

            dataCache.put('status', [
               {
                   text: 'Sử dụng',
                   value: 10
               },
               {
                   text: 'Không sử dụng',
                   value: 0
               }
            ]);


            dataCache.put('gioitinh', [
               {
                   text: 'Nam',
                   value: 1
               },
               {
                   text: 'Nữ',
                   value: 0
               }
            ]);

            dataCache.put('statusStr', [
               {
                   text: 'Sử dụng',
                   value: 1
               },
               {
                   text: 'Không sử dụng',
                   value: 0
               }
            ]);

            //loại đơn vị
            dataCache.put('typeUnit', [
               {
                   text: 'Thanh tra bộ',
                   value: 'TTR_B',
                   key: 0
               },
               {
                   text: 'Thanh tra không thuộc bộ',
                   value: 'TTR_K_B',
                   key: 1
               }
            ]);
            //loại sys từ điển
            dataCache.put('typeSysTuDien', [
               {
                   Text: 'Kế hoạch thanh tra',
                   Value: 'KEHOACHTHANHTRA',
               },
               {
                   Text: 'Loại thanh tra',
                   Value: 'LOAITHANHTRA',
               },
               {
                   Text: 'Nhóm thanh tra',
                   Value: 'NHOMTHANHTRA',
               },
               {
                   Text: 'Đối tượng thanh tra',
                   Value: 'DOITUONG',
               },
            ]);

            // customize message filter
            dataCache.put('operators', [
               {
                   string: {
                       eq: "Bằng",
                       neq: "Không bằng",
                       isnull: "Rỗng",
                       isnotnull: "Khác rỗng",
                       isempty: "Trống",
                       isnotempty: "Không trống",
                       startswith: "Bắt đầu bằng",
                       contains: "Chứa",
                       doesnotcontain: "Không chứa",
                       endswith: "Kết thúc bằng"
                   }
               }
            ]);

        }
        var result = {
            dateFormat: 'dd/MM/yyyy',
            delegateEvent: function ($event) {
                $event.preventDefault();
                $event.stopPropagation();
            }
        };
        result.tempData = function (name) {
            return dataCache.get(name);
        }
        result.putTempData = function (module, data) {
            dataCache.put(module, data);
        }
        return result;
    }
    ]);
    return app;
});
