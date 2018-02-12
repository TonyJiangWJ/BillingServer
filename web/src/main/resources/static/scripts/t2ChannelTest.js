/**
 * Created by jiangwj20966 on 2017/5/15.
 */
var host = '127.0.0.1';
var checkFunction = '/common/cep?functionId=';
var showService = '/manager/cep?pluginId=jres.cepcore&commandName=queryProcServices';
var showConnection = '/manager/cep?pluginId=jres.t2channel&commandName=queryAllConnections';
var $host = $('#host');
$(function () {
    $host.val(host);
    var h = $(window).height();
    var tableH = $('#tt').height();
    console.log(h + " " + tableH);
    $('#myFrame').height(h - tableH - 50);

    $("#checkFunc").click(function (event) {
        console.log("clicked checkFunc");
        checkFunc()
    });
    $("#getAllService").click(function (event) {
        console.log("clicked getAllService");
        getAllService()
    });
    $("#getAllConnect").click(function (event) {
        console.log("clicked getAllConnect");
        getAllConnection()
    });
    $("#checkAll").click(function (event) {
        console.log("clicked checkAll");
        checkAll()
    });
    $("#server").click(function (event) {
        $('#port').val('28110');
        $('#serverName').val("server")
    });
    $("#order").click(function (event) {
        $('#port').val('28111');
        $('#serverName').val("order")
    });
    $("#settle").click(function (event) {
        $('#port').val('28112');
        $('#serverName').val("settle")
    });
    $('#refresh').click(function (event) {
        var $target = $('#myFrame');
        var target = $target.attr("src");
        $target.attr("src", "");
        setTimeout(function () {
            console.log("delayed" + target);
            $('#myFrame').attr("src", target)
        }, 120);

    })

});

function getHost() {
    if ($host.val() === "undefined" || $host.val() === "") {
        return "http://"+host+":";
    } else {
        return "http://"+$host.val()+":";
    }
}

function checkFunc() {
    var port = $('#port').val();
    var funcId = $('#functionId').val();
    // window.location = host+port+checkFunction+funcId+"#"+$('#serverName').val()
    $('#myFrame').attr('src', getHost() + port + checkFunction + funcId + "#" + $('#serverName').val())
}

function getAllConnection() {
    var port = $('#port').val();
    $('#myFrame').attr('src', getHost() + port + showConnection + "#" + $('#serverName').val())
}

function getAllService() {
    var port = $('#port').val();
    $('#myFrame').attr('src', getHost() + port + showService + "#" + $('#serverName').val())
}

function checkAll() {
    var port = $('#port').val();
    $('#myFrame').attr('src', getHost() + port + "/unittest/cep?cmdId=all" + "#" + $("#serverName").val())
}