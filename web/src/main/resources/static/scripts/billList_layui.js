var totalPage;
var pageSize;
var pageNo;
var totalItem;
var isDelete;
var inOutType;
var startDate;
var endDate;
var sort;
var orderBy;
var isHidden;
var content;

layui.use(['laydate','table', 'layer'], function () {
    var laydate = layui.laydate;
    var $ = layui.$;
    var table = layui.table;
    //年月选择器
    laydate.render({
        elem: '#startMonth'
        , type: 'month'
    });
    laydate.render({
        elem: '#endMonth'
        , type: 'month'
    });
    laydate.render({
        elem: '#startDate'
    });
    laydate.render({
        elem: '#endDate'
    });
    laydate.render({
        elem: '#tradeDateHidden'
    });
    isDelete = 0;
    inOutType = '支出';
    ajaxRequest("/bootDemo/page/get", "get", {
        "isDelete": isDelete,
        "inOutType": inOutType
    }, function (response) {
        // console.log(JSON.stringify(response));
        if (response.msg === '成功') {
            loadInfo(response);
        }
    }, true);

    function loadInfo(response) {
        table.render({
            elem: '#billsList'
            , cols: [[ //标题栏
                {type:'checkbox'},
                {field: 'tradeNo', title: '订单编号', minWidth: 120}
                , {field: 'createTime', title: '创建时间', minWidth: 120}
                , {field: 'target', title: '交易对方', minWidth: 150}
                , {field: 'orderType', title: '交易类型', minWidth: 160}
                , {field: 'goodsName', title: '商品名称', minWidth: 80}
                , {field: 'memo', title: '备注', minWidth: 100}
                , {field: 'money', title: '金额', minWidth: 100}
                , {field: 'inOutType', title: '支出/收入', minWidth: 60}
                , {field: 'isDelete', title: '是否已删除', minWidth: 60, templet: function(costRecord){
                    return (costRecord.isDelete === 0 ? '未删除' : '已删除')
                }}
                , {field: 'isHidden', title: '显示状态', minWidth: 60}
            ]],
            data: response.costRecordList
            ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                ,groups: 5 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页

            }
        });
    }

    function reload(data) {
        $('#billList').html(loadListFlash);
        ajaxRequest("/bootDemo/page/get", "get", data, function (response) {
            if (response.msg === '成功') {
                loadInfo(response);
            }
        }, true);
    }

    function generateData() {
        return {
            "pageNo": pageNo,
            "pageSize": pageSize,
            "isDelete": isDelete,
            "isHidden": isHidden,
            "inOutType": inOutType,
            "startDate": startDate,
            "endDate": endDate,
            "sort": sort,
            "orderBy": orderBy,
            "content": content
        };
    }

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

});

function isNotEmpty(val) {
    return val !== null && typeof val !== 'undefined' && val !== '';
}