function SOAPClientParameters() {
    var _pl = new Array();
    this.add = function (name, value) {
        _pl[name] = value;
        return this;
    }
    this.toXml = function () {
        var xml = "";
        for (var p in _pl) {
            switch (typeof (_pl[p])) {
                case "string":
                case "number":
                case "boolean":
                case "object":
                    xml += "<" + p + ">" + SOAPClientParameters._serialize(_pl[p]) + "</" + p + ">";
                    break;
                default:
                    break;
            }
        }
        return xml;
    }
}
SOAPClientParameters._serialize = function (o) {
    var s = "";
    switch (typeof (o)) {
        case "string":
            //s += o.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"); break;
        case "number":
        case "boolean":
            s += o.toString(); break;
        case "object":
            // Date
            if (o.constructor.toString().indexOf("function Date()") > -1) {

                var year = o.getFullYear().toString();
                var month = (o.getMonth() + 1).toString(); month = (month.length == 1) ? "0" + month : month;
                var date = o.getDate().toString(); date = (date.length == 1) ? "0" + date : date;
                var hours = o.getHours().toString(); hours = (hours.length == 1) ? "0" + hours : hours;
                var minutes = o.getMinutes().toString(); minutes = (minutes.length == 1) ? "0" + minutes : minutes;
                var seconds = o.getSeconds().toString(); seconds = (seconds.length == 1) ? "0" + seconds : seconds;
                var milliseconds = o.getMilliseconds().toString();
                var tzminutes = Math.abs(o.getTimezoneOffset());
                var tzhours = 0;
                while (tzminutes >= 60) {
                    tzhours++;
                    tzminutes -= 60;
                }
                tzminutes = (tzminutes.toString().length == 1) ? "0" + tzminutes.toString() : tzminutes.toString();
                tzhours = (tzhours.toString().length == 1) ? "0" + tzhours.toString() : tzhours.toString();
                var timezone = ((o.getTimezoneOffset() < 0) ? "+" : "-") + tzhours + ":" + tzminutes;
                s += year + "-" + month + "-" + date + "T" + hours + ":" + minutes + ":" + seconds + "." + milliseconds + timezone;
            }
                // Array
            else if (o.constructor.toString().indexOf("function Array()") > -1) {
                for (var p in o) {
                    if (!isNaN(p))   // linear array
                    {
                        (/function\s+(\w*)\s*\(/ig).exec(o[p].constructor.toString());
                        var type = RegExp.$1;
                        switch (type) {
                            case "":
                                type = typeof (o[p]);
                            case "String":
                                type = "string"; break;
                            case "Number":
                                type = "int"; break;
                            case "Boolean":
                                type = "bool"; break;
                            case "Date":
                                type = "DateTime"; break;
                        }
                        s += "<" + type + ">" + SOAPClientParameters._serialize(o[p]) + "</" + type + ">"
                    }
                    else    // associative array
                        s += "<" + p + ">" + SOAPClientParameters._serialize(o[p]) + "</" + p + ">"
                }
            }
                // Object or custom function
            else
                for (var p in o)
                    s += "<" + p + ">" + SOAPClientParameters._serialize(o[p]) + "</" + p + ">";
            break;
        default:
            break; // throw new Error(500, "SOAPClientParameters: type '" + typeof(o) + "' is not supported");
    }
    return s;
}

function SOAPClient() { }

SOAPClient.count = 0;

SOAPClient.invoke = function (url, method, parameters, async, callback) {
    SOAPClient.count = 0;
    if (async)
        //SOAPClient._loadWsdl(url, method, parameters, async, callback);
        SOAPClient._sendSoapRequest(url, method, parameters, async, callback);
    else
        //return SOAPClient._loadWsdl(url, method, parameters, async, callback);
        return SOAPClient._sendSoapRequest(url, method, parameters, async, callback);
}

SOAPClient._sendSoapRequest = function (url, method, parameters, async, callback) {
    var soapdata = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"><soap12:Body>" +
        "<" + method + " xmlns=\"https://www.customs.gov.vn\">" + parameters.toXml() + "</" + method + "></soap12:Body></soap12:Envelope>";
    // send request
    var xmlHttp = SOAPClient._createCORSRequest(url);
    xmlHttp.timeout = 5000;
    xmlHttp.ontimeout = function() {
        SOAPClient.count++;
    };
    if (async) {
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState == 4)
                SOAPClient._onSendSoapRequest(url, method, parameters, async, callback, xmlHttp);
        }
    }
    xmlHttp.send(soapdata);
    if (!async)
        return SOAPClient._onSendSoapRequest(url, method, parameters, async, callback, xmlHttp);
}

SOAPClient._onSendSoapRequest = function (url, method, parameters, async, callback, req) {
    if (req.status === 200) {
        callback(req.responseXML, req.responseXML);
    } else {
        SOAPClient.count++;
        console.log("COUNT:" + SOAPClient.count);
        if (SOAPClient.count == 5) {
            callback("500", "500");
        }
        SOAPClient._sendSoapRequest(url, method, parameters, async, callback);
    }

}

SOAPClient._createCORSRequest = function (url) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        // XHR for Chrome/Firefox/Opera/Safari.
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/soap+xml; charset=utf-8");
    } else if (typeof XDomainRequest != "undefined") {
        // XDomainRequest for IE.
        xhr = new XDomainRequest();
        xhr.open("POST", url);
        xhr.setRequestHeader("Content-Type", "application/soap+xml; charset=utf-8");
    } else {
        // CORS not supported.
        xhr = null;
    }
    return xhr;
}