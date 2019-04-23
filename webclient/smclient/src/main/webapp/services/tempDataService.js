define(['angular'], function () {
    var app = angular.module('tempDataModule', []);

    app.factory('tempDataService', ['CacheFactory', function (CacheFactory) {
        var dataCache;
        if (!CacheFactory.get('dataCache')) {
            dataCache = CacheFactory('dataCache');
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

            //loại sys từ điển
            dataCache.put('day', [
            	{
            		value :'2',
            		status : false
        		},
            	{
            		value :'3',
            		status : false
        		},
            	{
            		value :'4',
            		status : false
        		},
            	{
            		value :'5',
            		status : false
        		},
            	{
            		value :'6',
            		status : false
        		},
            	{
            		value :'7',
            		status : false
        		},
            	{
            		value :'C',
            		status : false
        		},
            ]);

            //loại sys từ điển
            dataCache.put('loaiTaiKhoan', [
               {
                   Text: 'Quản trị hệ thống',
                   Value: 1,
               },
               {
                   Text: 'Quản trị đơn vị',
                   Value: 2,
               },
               {
                   Text: 'Người dùng đơn vị',
                   Value: 3,
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
