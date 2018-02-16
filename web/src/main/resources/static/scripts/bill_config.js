/**
 * Created by TonyJiang on 2017/6/14.
 */
var serverHost = "http://localserver:1011";
var pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEo9qWXCI89ldwzLvTFNdbXAlIWHJmypkk+hxCZaKvtbIGvJTzDYjaRjrjGXYkpWd/AHZ9CPqFJxcNGq/rbCUDcptHLZsyjooYQ0m7TvRjRyMQHmBJWPZp7CrKqhsPwu4CU2fbbBTc99S7Uqdt5kOGl/KmeGT5tAUFzl1QQCionQIDAQAB";

function ajaxRequest(requestUrl, method, data, callback, carryCookie) {
    if (carryCookie) {
        $.ajax({
                type: method,
                url: serverHost + requestUrl,
                xhrFields: {
                    withCredentials: true  //支持附带详细信息
                },
                data: data,
                success: function (resp) {
                    if (resp.code === "0006") {
                        $('#loginDialog').modal("show");
                    } else {
                        callback(resp);
                    }
                },
                error: function (resp) {
                    console.log("request fail:" + requestUrl);
                }
            }
        )
    } else {
        $.ajax({
            type: method,
            url: serverHost + requestUrl,
            data: data,
            success: callback,
            error: function (resp) {
                console.log("request fail:" + requestUrl);
            }
        })
    }
}

function getRsaCipher(str) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(pubKey);
    var timestamp = new Date().getTime();
    return encrypt.encrypt(timestamp+str);
}

