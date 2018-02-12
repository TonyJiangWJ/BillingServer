/**
 * Created by TonyJiang on 2017/10/21.
 */

layui.use('jquery', function () {
    var $ = layui.$;

    $(function () {
        $('#Timestamp13').val(new Date().getTime());
        // $('#startDate').datepicker().datepicker('option','dateFormat','yy-mm-dd').val(new Date('2013-10-11').pattern("yyyy-MM-dd"));
        // $('#endDate').datepicker().datepicker('option','dateFormat','yy-mm-dd').val(new Date().pattern("yyyy-MM-dd"));
        $("#nowDateTime").val(new Date().pattern("yyyy-MM-dd HH:mm:ss"));

        $('#ttString').click(function (event) {
            var stamp = $('#Timestamp13').val();
            stamp = parseFloat(stamp);
            var str = new Date(stamp).pattern("yyyy-MM-dd HH:mm:ss");
            $('#TimeString').val(str)
        });

        $('#ttStamp').click(function (event) {
            var str = $('#TimeString').val();
            var stamp = new Date(str).getTime();
            $('#Timestamp13').val(stamp)
        });

        $('#doEncode').click(function (event) {
            var simpleStr = $('#simpleStr').val();
            var encodeStr = encodeURI(simpleStr);
            $('#encodeStr').val(encodeStr)
        });

        $('#doDecode').click(function (event) {
            var encodeStr = $('#encodeStr').val();
            var simpleStr = decodeURI(encodeStr);
            $('#simpleStr').val(simpleStr)
        });

        $('#convert').click(function (event) {
            var unicodeStr = $('#unicodeStr').val();
            var $target = $('#convertedStr');
            $target.val(convertUtf8(unicodeStr));
        });

        $('#backUnicode').click(function (event) {
            var $target = $('#unicodeStr');
            var simpleStr = $('#convertedStr').val();
            simpleStr = toHexString(simpleStr);
            var regex = /%(u[\dabcdefABCDEF]{4})/;
            while (regex.test(simpleStr)) {
                simpleStr = simpleStr.replace(regex, '\\$1');
            }
            simpleStr = unescape(simpleStr);
            $target.val(simpleStr);
        });

        $('#calDayBtw').click(function (event) {
            var startDateStr = $('#startDate').val();
            var endDateStr = $('#endDate').val();
            var timeBetween = (new Date(endDateStr) - new Date(startDateStr)) / (3600 * 24 * 1000);
            console.log("间隔" + timeBetween + "天");
            $('#displayDays').text("间隔时间：" + timeBetween);
        });

        $('#calTargetDate').click(function (event) {
            var startDate = new Date($('#startDate').val());
            var days = $('#daysBtw').val();
            var targetDate = new Date(startDate.getTime() + 3600 * 24 * 1000 * parseInt(days));
            $('#endDate').val(new Date(targetDate).pattern("yyyy-MM-dd"));
            $('#endDateDisplay').val(new Date(targetDate).pattern("yyyy-MM-dd"));
            $('#displayDays').text(new Date(targetDate).pattern('yyyy-MM-dd EE'));
        });

        $('#encodeBase64').click(function (event) {
            var encodeStr = new MyBase64().encode($('#base64Source').val());
            $('#encoded64').val(encodeStr);
        });

        $('#decodeBase64').click(function (event) {
            var decodeStr = new MyBase64().decode($('#encoded64').val());
            $('#base64Source').val(decodeStr);
        });
        setInterval('$("#nowDateTime").text(new Date().pattern("yyyy-MM-dd HH:mm:ss"));', 1000);

        // $('#jsonParse').click(function(event){
        //    var sourceStr = $('#sourceStr').val();
        //    var json = JSON.object;
        //    var jsonStr = "";
        //    try{
        //         json = JSON.parse(sourceStr);
        //         jsonStr = JSON.stringify(json,null,4);
        //    }catch (e){
        //        jsonStr = "非法JSON字符串";
        //    }
        //    $('#jsonStr').val(jsonStr);
        // });
        $('#encodeMd5').click(function (event) {
            var source = $('#md5source').val();
            $('#md5_32').val(source.MD5(32));
            $('#md5_16').val(source.MD5(16));

        });

        $('#generateRsaKeys').click(function(event){
            var encrypt = new JSEncrypt();
            $('#publicKey').val(encrypt.getPublicKey());
            $('#privateKey').val(encrypt.getPrivateKey());
        });

        $('#pubEncryptRsa').on("click", function (event) {
            var $decrypt = $('#rsaDecryptContent');
            var $encrypt = $('#rsaEncryptContent');
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey($('#publicKey').val());
            $encrypt.val(encrypt.encrypt($decrypt.val()));
        });

        $('#priDecryptRsa').on("click", function (event) {
            var $decrypt = $('#rsaDecryptContent');
            var $encrypt = $('#rsaEncryptContent');
            var encrypt = new JSEncrypt();
            encrypt.setPrivateKey($('#privateKey').val());
            $decrypt.val(encrypt.decrypt($encrypt.val()));
        });

        // $('#priEncryptRsa').on("click", function (event) {
        //     var $decrypt = $('#rsaDecryptContent');
        //     var $encrypt = $('#rsaEncryptContent');
        //     var encrypt = new JSEncrypt();
        //     encrypt.setPrivateKey($('#privateKey').val());
        //     $encrypt.val(encrypt.encrypt($decrypt.val()));
        // });

        // $('#pubDecryptRsa').on("click", function (event) {
        //     var $decrypt = $('#rsaDecryptContent');
        //     var $encrypt = $('#rsaEncryptContent');
        //     var encrypt = new JSEncrypt();
        //     encrypt.setPublicKey($('#publicKey').val());
        //     $decrypt.val(encrypt.decrypt($encrypt.val()));
        // });

        $('#escapeForCN').click(function (event) {
            var src = $('#srcStr').val();
            $('#destStr').val(src.replace(/\s/g,''));
        });

        $('#escapeForEN').click(function (event) {
            var src = $('#srcStr').val();
            $('#destStr').val(src.replace(/(\n)|(\r\n)/g,''));
        });

    });

    function convertUtf8(encodeStr) {
        var regex = /(\\u)([\dabcdefABCDEF]{4})/;
        // regxe.match(encodeStr)
        while (regex.test(encodeStr)) {
            var matchList = regex.exec(encodeStr);
            var c = matchList[2];
            var s = String.fromCharCode(parseInt(c, 16));
            encodeStr = encodeStr.replace(regex, s);
        }
        return encodeStr;
    }

    function toHexString(str) {
        return escape(str);
    }

    function dblClick() {
        var timeStr = $('#nowDateTime').text();
        var time = new Date().getTime();
        console.log(time);
        $('#TimeString').val(timeStr);
        $('#Timestamp13').val(time);
    }

    function toggleMd32Case() {
        var $str = $('#md5_32');
        var str = $str.val();
        if (/[a-z]/.test(str)) {
            $str.val(str.toUpperCase());
        } else {
            $str.val(str.toLowerCase());
        }
    }

    function toggleMd16Case() {
        var $str = $('#md5_16');
        var str = $str.val();
        if (/[a-z]/.test(str)) {
            $str.val(str.toUpperCase());
        } else {
            $str.val(str.toLowerCase());
        }
    }

});