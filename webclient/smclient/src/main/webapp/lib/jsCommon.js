Array.prototype.clear = function () {
    this.length = 0;
}

window.notify = function (message) {
    if (message) {
        console.info(message);
    } else {
        console.info("notify!");
    }
};

window.CacheManager = function () {
    var result = {};
    var cacheStatus = {};

    var register = function (name, initData, updateFunc) {
        var target = cacheStatus[name];
        if (!target) {
            target = {
                updated: false,
                updateFunc: updateFunc,
                data: [],
            }
            cacheStatus[name] = target;
        }

        if (initData) {
        //    initData.unshift({ id: null, value: null, text: '--' });
            target.updated = true;
            target.data = initData;
            result[name] = initData;
        } else if (!target.updated && target.updateFunc) {
            initData = target.updateFunc();
         //   initData.unshift({ id: null, value: null, text: '--' });
            target.updated = true;
            target.data = initData;
            result[name] = initData;
        }
    };
    var updateAll = function () {
        for (key in cacheStatus) {
            result.update(key);
        }
    };
    var update = function (name, callback) {
        var target = cacheStatus[name];
        if (target && target.updateFunc) {
            target.updated = false;
            var funcLink = target.updateFunc();
            if (funcLink.$promise) {
                funcLink.$promise.then(function (response) {
                    target.updated = true;
                    target.data = response;
                    result[name] = response;
                    if (callback) {
                        callback();
                    }
                })
            }
            else {
                var initData = target.updateFunc();
                target.updated = true;
                target.data = initData;
                result[name] = initData;
                if (callback) {
                    callback();
                }
            }
        }
    };

    result.register = register;
    result.updateAll = updateAll;
    result.update = update;

    return result;
}