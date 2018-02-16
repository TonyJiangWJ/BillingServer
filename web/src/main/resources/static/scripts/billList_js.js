/**
 * Created by TonyJiang on 2017/6/10.
 */

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

var loadListFlash = '<tr><td></td><td></td><td></td><td></td><td></td><td><div id="loadListFlash" class="sk-circle" style="display:block;"><div class="sk-circle1 sk-child"></div><div class="sk-circle2 sk-child"></div><div class="sk-circle3 sk-child"></div><div class="sk-circle4 sk-child"></div><div class="sk-circle5 sk-child"></div><div class="sk-circle6 sk-child"></div><div class="sk-circle7 sk-child"></div><div class="sk-circle8 sk-child"></div><div class="sk-circle9 sk-child"></div><div class="sk-circle10 sk-child"></div><div class="sk-circle11 sk-child"></div><div class="sk-circle12 sk-child"></div></div></td><td></td><td></td><td></td><td></td><td></td></tr> ';
var loadReportFlash = '<tr><td></td><td></td><td></td><td></td><td><div id="loadReportFlash" class="sk-circle" style="display:block;"><div class="sk-circle1 sk-child"></div><div class="sk-circle2 sk-child"></div><div class="sk-circle3 sk-child"></div><div class="sk-circle4 sk-child"></div><div class="sk-circle5 sk-child"></div><div class="sk-circle6 sk-child"></div><div class="sk-circle7 sk-child"></div><div class="sk-circle8 sk-child"></div><div class="sk-circle9 sk-child"></div><div class="sk-circle10 sk-child"></div><div class="sk-circle11 sk-child"></div><div class="sk-circle12 sk-child"></div></div></td><td></td><td></td><td></td><td></td></tr>'
$(function () {
//        $('#dialog').dialog();
    loadReport();
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
});


$('#totalItem').dblclick(function () {
    $('#pageSize').val($(this).text().substring(3));
});

$('#showInOnly').click(function () {
    inOutType = "收入";
    clearPageNo();
    reload(generateData())
});
$('#showOutOnly').click(function () {
    clearPageNo();
    inOutType = "支出";
    reload(generateData())
});
$('#showAllInOut').click(function () {
    clearPageNo();
    inOutType = '';
    reload(generateData())
});

$('#showDisplayOnly').click(function () {
    isHidden = 0;
    clearPageNo();
    reload(generateData())
});

$('#showHiddenOnly').click(function () {
    isHidden = 1;
    clearPageNo();
    reload(generateData())
});

$('#showAllDisplayStatus').click(function () {
    isHidden = '';
    clearPageNo();
    reload(generateData())
});

$(document).delegate('#jumpTo', 'click', function (event) {
    var targetPage = $('#targetPage').val();
    // console.log("jump to " + targetPage);
    if (targetPage !== 'undefined' && targetPage !== '') {
        pageNo = targetPage;
        reload(generateData());
    }
});

$(document).delegate('#nextPage', 'click', function (event) {
    if (pageNo === 0) {
        pageNo = 1;
    }
    if (pageNo < totalPage) {
        pageNo = pageNo + 1;
        reload(generateData());
    }
});

$(document).delegate('#prePage', 'click', function (event) {
    if (pageNo > 1) {
        pageNo -= 1;
        reload(generateData());
    }
});
$(document).delegate('#hideDeleted', 'click', function (event) {
    clearPageNo();
    isDelete = 0;
    reload(generateData());
});

$(document).delegate('#showDeleted', 'click', function (event) {
    clearPageNo();
    isDelete = '';
    reload(generateData());
});
$(document).delegate('#deletedOnly', 'click', function (event) {
    clearPageNo();
    isDelete = 1;
    reload(generateData());
});
$(document).delegate('#setPageSize', 'click', function (event) {
    clearPageNo();
    var pSize = $('#pageSize').val();
    if (pSize !== '' && pSize > 2) {
        pageSize = pSize;
        reload(generateData());
    }
});


function reload(data) {
    $('#billList').html(loadListFlash);
    ajaxRequest("/bootDemo/page/get", "get", data, function (response) {
        if (response.msg === '成功') {
            loadInfo(response);
        }
    }, true);
}

function loadReport() {
    $('#reportDetail').html(loadReportFlash);
    var startMonth = $('#startMonth').val();
    var endMonth = $('#endMonth').val();
    ajaxRequest("/bootDemo/report/get", "get", {"startMonth": startMonth, "endMonth": endMonth}, function (response) {
        if (response.code === '0001') {
            // // console.log(JSON.stringify(response));
            // drawCanvas(response);
            renderEChart(response); // 填充echarts图表
            var totalCost = 0;
            var totalCostExceptDeleted = 0;
            var totalCostExceptHidden = 0;
            var totalCostExceptDeletedAndHidden = 0;
            var totalIncome = 0;
            var totalIncomeExceptDeleted = 0;
            var totalIncomeExceptHidden = 0;
            var totalIncomeExceptDeletedAndHidden = 0;
            var totalIncomeAndCost = 0;
            var reportList = response.reportList;
            var html = "";
            for (var i = 0; i < reportList.length; i++) {
                var report = reportList[i];
                if (!isNotEmpty(startMonth) && report.month === "2017-01") {
                    continue;
                }
                html += '<tr>';
                html += '<td>' + getDisplayNum(report.month) + '</td>';
                html += '<td>' + getDisplayNum(report.totalCost) + '</td>';
                totalCost += getValue(report.totalCost);
                html += '<td>' + getDisplayNum(report.totalCostExceptDeleted) + '</td>';
                totalCostExceptDeleted += getValue(report.totalCostExceptDeleted);
                html += '<td>' + getDisplayNum(report.totalCostExceptHidden) + '</td>';
                totalCostExceptHidden += getValue(report.totalCostExceptHidden);
                html += '<td>' + getDisplayNum(report.totalCostExceptDeletedAndHidden) + '</td>';
                totalCostExceptDeletedAndHidden += getValue(report.totalCostExceptDeletedAndHidden);
                html += '<td>' + getDisplayNum(report.totalIncome) + '</td>';
                totalIncome += getValue(report.totalIncome);
                html += '<td>' + getDisplayNum(report.totalIncomeExceptDeleted) + '</td>';
                totalIncomeExceptDeleted += getValue(report.totalIncomeExceptDeleted);
                html += '<td>' + getDisplayNum(report.totalIncomeExceptHidden) + '</td>';
                totalIncomeExceptHidden += getValue(report.totalIncomeExceptHidden);
                html += '<td>' + getDisplayNum(report.totalIncomeExceptDeletedAndHidden) + '</td>';
                totalIncomeExceptDeletedAndHidden += getValue(report.totalIncomeExceptDeletedAndHidden);
                html += '<td>' + getDisplayNum(parseFloat(getDisplayNum(report.totalIncomeExceptDeletedAndHidden)) - parseFloat(getDisplayNum(report.totalCostExceptDeletedAndHidden))).toFixed(2) + '</td>';
                totalIncomeAndCost += getDisplayNum(parseFloat(getDisplayNum(report.totalIncomeExceptDeletedAndHidden)) - parseFloat(getDisplayNum(report.totalCostExceptDeletedAndHidden)));
                html += '</tr>';
            }
            html += '<tr><td>合计</td><td>' + getDisplayNum(totalCost.toFixed(2)) + '</td><td>' + getDisplayNum(totalCostExceptDeleted.toFixed(2)) + '</td><td>' + getDisplayNum(totalCostExceptHidden.toFixed(2)) + '</td><td style="color: #b92c28">' + getDisplayNum(totalCostExceptDeletedAndHidden.toFixed(2)) +
                '</td><td>' + getDisplayNum(totalIncome.toFixed(2)) + '</td><td>' + getDisplayNum(totalIncomeExceptDeleted.toFixed(2)) + '</td><td>' + getDisplayNum(totalIncomeExceptHidden.toFixed(2)) + '</td><td style="color: #b92c28">' + getDisplayNum(totalIncomeExceptDeletedAndHidden.toFixed(2)) + '</td><td style="color: #b92c28">' + getDisplayNum(totalIncomeAndCost.toFixed(2)) + '</td></tr>';
            $('#reportDetail').html("").append(html);
        }
    }, true);
}

function calTotal(x, y) {
    if (x instanceof MonthReport && y instanceof MonthReport) {
        return x.amount + y.amount;
    } else if (y instanceof MonthReport) {
        return x + y.amount;
    } else {
        throw "Not Support";
    }
}

function avg(list) {
    var count = list.length;
    var total = list.reduce(calTotal);
    return (total / count).toFixed(2);
}

function calTotalCost(x, y) {
    if (typeof x === 'object') {
        return parseFloat(x.totalCost) + parseFloat(y.totalCost);
    } else {
        return x + parseFloat(y.totalCost);
    }
}

function calTotalIncome(x, y) {
    if (typeof x === 'object') {
        return parseFloat(x.totalIncome) + parseFloat(y.totalIncome);
    } else {
        return x + parseFloat(y.totalIncome);
    }
}

function calTotalCostExceptDH(x, y) {
    if (typeof x === 'object') {
        return parseFloat(x.totalCostExceptDeletedAndHidden) + parseFloat(y.totalCostExceptDeletedAndHidden);
    } else {
        return x + +parseFloat(y.totalCostExceptDeletedAndHidden);
    }
}

function calTotalIncomeExceptDH(x, y) {
    if (typeof x === 'object') {
        return parseFloat(x.totalIncomeExceptDeletedAndHidden) + parseFloat(y.totalIncomeExceptDeletedAndHidden);
    } else {
        return x + parseFloat(y.totalIncomeExceptDeletedAndHidden);
    }
}

function renderEChart(response) {
    var reportList = response.reportList;
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('chartsMain'));
    var sourceArray = [];
    sourceArray.push(['product', '总支出', '总收入', '净支出', '净收入', '平均总支出', '平均总收入', '平均净支出', '平均净收入']);
    var length = reportList.length;
    var totalCost = reportList.reduce(calTotalCost);
    var totalIncome = reportList.reduce(calTotalIncome);
    var totalCostExceptDeletedAndHidden = reportList.reduce(calTotalCostExceptDH);
    var totalIncomeExceptDeletedAndHidden = reportList.reduce(calTotalIncomeExceptDH);
    var avgCost = (totalCost / length).toFixed(2);
    var avgIncome = (totalIncome / length).toFixed(2);
    var avgCostEDH = (totalCostExceptDeletedAndHidden / length).toFixed(2);
    var avgIncomeEDH = (totalIncomeExceptDeletedAndHidden / length).toFixed(2);
    for (var i = 0; i < length; i++) {
        var reportItem = reportList[i];
        sourceArray.push([reportItem.month, reportItem.totalCost, reportItem.totalIncome,
            reportItem.totalCostExceptDeletedAndHidden, reportItem.totalIncomeExceptDeletedAndHidden,
            avgCost, avgIncome, avgCostEDH, avgIncomeEDH]);
    }
    console.log(sourceArray)
    // 指定图表的配置项和数据
    var option = {
        legend: {},
        tooltip: {},
        dataset: {
            // 提供一份数据。
            source: sourceArray
        },
        // 声明一个 X 轴，类目轴（category）。默认情况下，类目轴对应到 dataset 第一列。
        xAxis: {type: 'category'},
        // 声明一个 Y 轴，数值轴。
        yAxis: {},
        // 声明多个 bar 系列，默认情况下，每个系列会自动对应到 dataset 的每一列。
        series: [
            {type: 'line'},
            {type: 'line'},
            {
                type: 'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }
            },
            {
                type: 'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }
            },
            {
                type: 'line',
                areaStyle: {normal: {}},
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                }
            },
            {
                type: 'line',
                areaStyle: {normal: {}},
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                }
            },
            {
                type: 'line',
                areaStyle: {normal: {}},
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                }
            },
            {
                type: 'line',
                areaStyle: {normal: {}},
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}


function clearAllCanvas() {
    var canvasList = $('canvas');
    canvasList.each(function (idx, elemt) {
        var context = elemt.getContext('2d');
        context.fillStyle = "#ffffff";
        var width = elemt.width;
        var height = elemt.height;
        context.clearRect(0, 0, width, height);
    })
}

// 绘制canvas图表
function drawCanvas(response) {
    clearAllCanvas();
    var canvas = $('#report_canvas_cost')[0];
    var ctx = canvas.getContext("2d");
    const canvasHeight = canvas.height;
    const canvasWidth = canvas.width;
    var reportList = response.reportList;
    var totalCostList = [];
    var totalIncomeList = [];
    var totalCostExceptDeletedAndHidden = [];
    var totalIncomeExceptDeletedAndHidden = [];
    var max = -1;
    for (val of reportList) {
        var month = val.month;
        var cost = new MonthReport(val.totalCost, month);
        totalCostList.push(cost);
        if (max < parseFloat(cost.amount)) {
            max = parseFloat(cost.amount);
        }
        var income = new MonthReport(val.totalIncome, month);
        totalIncomeList.push(income);
        if (max < parseFloat(income.amount)) {
            max = parseFloat(income.amount);
        }
        var costExceptDeletedAndHidden = new MonthReport(val.totalCostExceptDeletedAndHidden, month);
        totalCostExceptDeletedAndHidden.push(costExceptDeletedAndHidden);
        if (max < parseFloat(costExceptDeletedAndHidden.amount)) {
            max = parseFloat(costExceptDeletedAndHidden.amount);
        }
        var incomeExceptDeletedAndHidden = new MonthReport(val.totalIncomeExceptDeletedAndHidden, month);
        totalIncomeExceptDeletedAndHidden.push(incomeExceptDeletedAndHidden);
        if (max < parseFloat(incomeExceptDeletedAndHidden.amount)) {
            max = parseFloat(incomeExceptDeletedAndHidden.amount);
        }
    }

    var maxY = (Math.ceil(max / 5000)) * 5000;
    var length = totalCostList.length;
    var gapWidth = Math.floor(canvasWidth / length);

    ctx.fillStyle = "#ffffff";
    ctx.fillRect(0, 0, canvasWidth, canvasHeight);

    ctx.fillStyle = "red";

    var path = new Path2D();
    var pathDesc = new Path2D();
    pathDesc.moveTo(10, 10);
    pathDesc.lineTo(100, 10);
    ctx.fillText("总支出 平均每月：" + avg(totalCostList), 110, 15);
    path.moveTo(0, canvasHeight - totalCostList[0]);
    for (var i = 0; i < totalCostList.length; i++) {
        var costInfo = totalCostList[i];
        var ptX = gapWidth * i;
        var ptY = getEndY(maxY, costInfo.amount, canvasHeight);
        path.lineTo(ptX, ptY);
        ctx.fillText(costInfo.amount, ptX, ptY);
        ctx.fillText(costInfo.month, ptX - 10, canvasHeight - 5);
    }
    ctx.stroke(path);
    ctx.stroke(pathDesc);


    ctx = $('#report_canvas_income')[0].getContext('2d');
    ctx.fillStyle = "green";
    ctx.strokeStyle = "#9dff7a";
    path = new Path2D();
    pathDesc = new Path2D();
    pathDesc.moveTo(10, 30);
    pathDesc.lineTo(100, 30);
    ctx.fillText("总收入 平均每月：" + avg(totalIncomeList), 110, 35);
    path.moveTo(0, canvasHeight - totalIncomeList[0]);
    for (var i = 0; i < totalIncomeList.length; i++) {
        var incomeInfo = totalIncomeList[i];
        var ptX = gapWidth * i;
        var ptY = getEndY(maxY, incomeInfo.amount, canvasHeight);
        path.lineTo(ptX, ptY);
        ctx.fillText(incomeInfo.amount, ptX, ptY);
    }
    ctx.stroke(path);
    ctx.stroke(pathDesc);

    ctx = $('#report_canvas_cost_clear')[0].getContext('2d');
    path = new Path2D();
    ctx.fillStyle = "#762c18";
    ctx.strokeStyle = "#ff1109";
    pathDesc = new Path2D();
    pathDesc.moveTo(10, 50);
    pathDesc.lineTo(100, 50);
    ctx.fillText("净支出 平均每月：" + avg(totalCostExceptDeletedAndHidden), 110, 55);
    path.moveTo(0, canvasHeight - totalCostExceptDeletedAndHidden[0]);
    for (var i = 0; i < totalCostExceptDeletedAndHidden.length; i++) {
        var info = totalCostExceptDeletedAndHidden[i];
        var ptX = gapWidth * i;
        var ptY = getEndY(maxY, info.amount, canvasHeight);
        path.lineTo(ptX, ptY);
        ctx.fillText(info.amount, ptX, ptY);
    }
    ctx.stroke(path);
    ctx.stroke(pathDesc);


    ctx = $('#report_canvas_income_clear')[0].getContext('2d');
    path = new Path2D();
    ctx.fillStyle = "#547376";
    ctx.strokeStyle = "#01e9ff";

    path.moveTo(0, canvasHeight - totalIncomeExceptDeletedAndHidden[0]);
    for (var i = 0; i < totalIncomeExceptDeletedAndHidden.length; i++) {
        var info = totalIncomeExceptDeletedAndHidden[i];
        var ptX = gapWidth * i;
        var ptY = getEndY(maxY, info.amount, canvasHeight);
        path.lineTo(ptX, ptY);
        ctx.fillText(info.amount, ptX, ptY);
    }
    pathDesc = new Path2D();
    pathDesc.moveTo(10, 70);
    pathDesc.lineTo(100, 70);
    ctx.fillText("净收入 平均每月：" + avg(totalIncomeExceptDeletedAndHidden), 110, 75);
    ctx.stroke(path);
    ctx.stroke(pathDesc);
}

function getEndY(maxY, val, canvasHeight) {
    return canvasHeight * (1 - val / maxY);
}

function MonthReport(amount, month) {
    this.amount = parseFloat(amount);
    this.month = month;
}

function toggleCost() {
    $('#report_canvas_cost').toggle("hide");
}

function toggleIncome() {
    $('#report_canvas_income').toggle("hide");
}

function toggleCostClear() {
    $('#report_canvas_cost_clear').toggle("hide");
}

function toggleIncomeClear() {
    $('#report_canvas_income_clear').toggle("hide");
}

function getValue(val) {
    var v = getDisplayNum(val);
    return parseFloat(v);
}

function delayTime() {
    for (var i = 0; i < 10000000; i++) {
        Math.sqrt(Math.random(i));
    }
}

function loadInfo(response) {
    delayTime();
    var result = response.costRecordList;
    totalPage = response.totalPage;
    pageSize = response.pageSize;
    pageNo = response.pageNo;
    totalItem = response.totalItem;
    var $tbody = $('#billList');
//        // console.log('costList length:' + result.length);
    $tbody.html("");
    for (var idx in result) {
        var costRecord = result[idx];
        var bodyHtml = '<tr id="' + idx + 'hh">';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.tradeNo) + ' 点击修改订单内容">' + costRecord.tradeNo + '</td>';
        bodyHtml += '<td title="' + getDisplayWeekStr(costRecord.createTime) + '">' + getDisplayStr(costRecord.createTime) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.target) + '">' + getDisplayStr(costRecord.target) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.orderType) + '">' + getDisplayStr(costRecord.orderType) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.goodsName) + '">' + getDisplayStr(costRecord.goodsName) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.memo) + '">' + getDisplayStr(costRecord.memo) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.money) + '">' + getDisplayStr(costRecord.money) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.inOutType) + '">' + getDisplayStr(costRecord.inOutType) + '</td>';
        bodyHtml += '<td title="' + getDisplayStr(costRecord.orderStatus) + '">' + getDisplayStr(costRecord.orderStatus) + '</td>';
        bodyHtml += '<td>' + (costRecord.isDelete === 0 ? '未删除' : '已删除') + '</td>';
        bodyHtml += '<td>' + getDisplayStr(costRecord.isHidden) + '</td></tr>';
//            // console.log(bodyHtml);
        $tbody.append(bodyHtml);
    }
    $tbody.append('<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>' + response.currentAmount + '</td><td></td><td></td><td></td><td></td></tr>');
    delegateAllClickEvent(result.length);
    $('#totalPage').text('每页显示：' + pageSize);
    $('#totalItem').text('总数：' + totalItem);
    $('#pageNo').text('当前页：' + (pageNo === 0 ? 1 : pageNo) + '/' + totalPage);
    initTableCheckbox();
}

function delegateAllClickEvent(idxSize) {
    for (var i = 0; i < idxSize; i++) {
        var $tableRow = $('#' + i + 'hh');
        $tableRow.undelegate();
        $tableRow.delegate('td:nth-child(2)', 'click', function (event) {
//                // console.log();
            var tradeNo = $(this).text();
            ajaxRequest("/bootDemo/detail/get", "get", {"tradeNo": tradeNo}, function (response) {
                if (response.msg === "成功") {
//                            // console.log(JSON.stringify(response.recordDetail));
//                            try {
//                                var json = JSON.parse(response.recordDetail);
//                                var jsonStr = JSON.stringify(json, null, 4);
//
//                                $('#detail').text("asd" + jsonStr);
//                            } catch (e) {
                    var $dialog = $('#dialog');
                    var detail = response.recordDetail;
                    var detailHtml = '<table class="t-table" cellspacing="10">'
                        + '<thead>'
                        + '<tr>'
                        + '<th>属性</th>'
                        + '<th>内容</th>'
                        + '</tr>'
                        + '</thead>'
                        + '<tbody>'
                        + '<tr>'
                        + '<td>账单编号</td>'
                        + '<td>' + getDisplayStr(detail.tradeNo) + '</td>'
                        + '<input type="hidden" id="dialog_tradeNo" value="' + getDisplayStr(detail.tradeNo) + '">'
                        + '</tr>'
                        + '<tr>'
                        + '<td>订单编号</td>'
                        + '<td>' + getDisplayStr(detail.orderNo) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>创建时间</td>'
                        + '<td>' + getDisplayStr(detail.createTime) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>支付时间</td>'
                        + '<td>' + getDisplayStr(detail.paidTime) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>修改时间</td>'
                        + '<td>' + getDisplayStr(detail.modifyTime) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>地点</td>'
                        + '<td><input class="t-input" type="text" id="dialog_location" value="' + getDisplayStr(detail.location) + '" readonly></td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>订单类型</td>'
                        + '<td>' + getDisplayStr(detail.orderType) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>交易对方</td>'
                        + '<td>' + getDisplayStr(detail.target) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>商品名</td>'
                        + '<td><input class="t-input" type="text" id="dialog_goodsName" value="' + getDisplayStr(detail.goodsName) + '" readonly></td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>备注</td>'
                        + '<td><input class="t-input" type="text" id="dialog_memo" value="' + getDisplayStr(detail.memo) + '" readonly></td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>金额</td>'
                        + '<td>' + getDisplayStr(detail.money) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>账单状态</td>'
                        + '<td>' + getDisplayStr(detail.tradeStatus) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>收支状态</td>'
                        + '<td>' + getDisplayStr(detail.inOutType) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>订单状态</td>'
                        + '<td>' + getDisplayStr(detail.orderStatus) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>服务手续费</td>'
                        + '<td>' + getDisplayStr(detail.serviceCost) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>退款</td>'
                        + '<td>' + getDisplayStr(detail.refundMoney) + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>是否已删除</td>'
                        + '<td>' + (detail.isDelete === 0 ? '未删除' : '已删除') + '</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td><button class="u-btn" id="dialog_edit" onclick="enable_edit()">启用修改</button>'
                        + '<button style="display:none;" class="u-btn u-btn-success" id="dialog_edit_save" onclick="doEditSave()">保存修改</button></td>'
                        + '<td><button class="u-btn" id="manageTag" onclick="manageTag()">管理标签</button></td>'
                        + '</tr>'
                        + '</tbody>'
                        + '</table>';
                    $('#billDetail').html('').append(detailHtml);
//                            $dialog.css({"width":"500px","height":"520px"});

                    ajaxRequest("/bootDemo/cost/tag/list", "get", {"tradeNo": tradeNo}, function (resp) {
                        var $tag_list = $('#billBindTagList');
                        $tag_list.html("");
                        if (resp.code === '0001') {

                            var tagList = resp.tagInfoModels;
                            for (var i = 0; i < tagList.length; i++) {
                                $tag_list.append(generateCostLi(tagList[i]));
                            }
                        }
                    }, true);


                    $dialog.modal("show");
                    $('#detail').text(JSON.stringify(response.recordDetail, null, 4));
//                            }
                }
            }, true);

        });
        $tableRow.delegate('td:nth-child(11)', 'click', function (event) {
            var tradeNo = $(this).parent().find('td:nth-child(2)').text();
            var nowStatusStr = $(this).text();
            var targetOperate = nowStatusStr === '已删除' ? "取消删除" : "删除";
            var nowStatus = nowStatusStr === '已删除' ? 1 : 0;
            if (confirm("是否确认" + targetOperate + "账单" + tradeNo)) {
                // console.log('删除订单' + tradeNo);
                ajaxRequest("/bootDemo/delete", "get", {
                    "tradeNo": tradeNo,
                    "nowStatus": nowStatus
                }, function (response) {
                    if (response.msg === "成功") {
                        // console.log(JSON.stringify(response));
                    }
                    reload(generateData());
                }, true);

            }

        });

        $tableRow.delegate('td:nth-child(12)', 'click', function (event) {
            var tradeNo = $(this).parent().find('td:nth-child(2)').text();
            var nowStatusStr = $(this).text();
            var targetOperate = nowStatusStr === '显示' ? "隐藏" : "显示";
            var nowStatus = nowStatusStr;
            if (confirm("是否确认" + targetOperate + "账单" + tradeNo)) {
                // console.log('隐藏订单' + tradeNo);
                ajaxRequest("/bootDemo/toggle/hide", "get", {
                    "tradeNo": tradeNo,
                    "nowStatus": nowStatus
                }, function (response) {
                    if (response.msg === "成功") {
                        // console.log(JSON.stringify(response));
                    }
                    reload(generateData());
                }, true);
            }

        });
    }
}

function manageTag() {
    // $('#dialog').modal("hide");
    var tradeNo = $('#dialog_tradeNo').val();
    $('#billTagDialogTradeNo').text(tradeNo);
    $('#billTagDialogGoodsName').text($('#dialog_goodsName').val());
    loadTagList(tradeNo);
    $('#billTagDialog').modal('show');
}

function loadTagList(tradeNo) {
    ajaxRequest("/bootDemo/cost/tag/list", "get", {"tradeNo": tradeNo}, function (resp) {
        var $tag_list = $('#billTagList');
        $tag_list.html("");
        if (resp.code === '0001') {
            var tagList = resp.tagInfoModels;
            for (var i = 0; i < tagList.length; i++) {
                $tag_list.append(generateCostLi(tagList[i]));
            }
        }
    }, true);
    ajaxRequest("/bootDemo/tag/list", "get", null, function (resp) {
        if (resp.code === "0001") {
            var $tag_list = $('#tag_list');
            $tag_list.html("");
            var tagList = resp.tagInfoList;
            for (var i = 0; i < tagList.length; i++) {
                $tag_list.append(generateLi(tagList[i]));
            }

        }
        // console.log(JSON.stringify(resp));
    }, true);

}

function generateLi(tagInfo) {
    return '<li class="t-li"><button class="btn btn-default" data-index="' + tagInfo.tagId + '">' + tagInfo.tagName + '</button></li>';
}

function generateCostLi(tagInfo) {
    return '<li class="t-li"><button class="btn btn-success" data-index="' + tagInfo.tagId + '">' + tagInfo.tagName + '</button></li>';
}

$(document).delegate("#tag_list > li > button", 'dblclick', function (event) {
    var tagId = $(this).attr("data-index");
    var tradeNo = $('#dialog_tradeNo').val();
    ajaxRequest("/bootDemo/cost/tag/put", "get", {"tradeNo": tradeNo, "tagId": tagId}, function (resp) {
        if (resp.code === '0001') {
            loadTagList(tradeNo);
        }
    }, true);
    // console.log(tagId);
});
$(document).delegate("#billTagList > li > button", 'dblclick', function (event) {
    var tagId = $(this).attr("data-index");
    var tradeNo = $('#dialog_tradeNo').val();
    ajaxRequest("/bootDemo/cost/tag/delete", "get", {"tradeNo": tradeNo, "tagId": tagId}, function (resp) {
        if (resp.code === '0001') {
            loadTagList(tradeNo);
        }
    }, true);
});

function refreshTagList() {
    ajaxRequest("/bootDemo/tag/list", "get", null, function (resp) {
        if (resp.code === "0001") {
            var $tag_list = $('#tag_list');
            $tag_list.html("");
            var tagList = resp.tagInfoList;
            for (var i = 0; i < tagList.length; i++) {
                $tag_list.append(generateLi(tagList[i]));
            }

        }
        // console.log(JSON.stringify(resp));
    }, true);
}

function enable_edit() {
    $('#dialog_goodsName').removeAttr("readonly");
    $('#dialog_memo').removeAttr("readonly");
    $('#dialog_location').removeAttr("readonly");
    $('#dialog_edit').css({"display": "none"});
    $('#dialog_edit_save').css({"display": "block"});
}

function doEditSave() {


    var tradeNo = $('#dialog_tradeNo').val();
    var goodsName = $('#dialog_goodsName').val();
    var memo = $('#dialog_memo').val();
    var location = $('#dialog_location').val();

    ajaxRequest("/bootDemo/record/update", "get", {
        "tradeNo": tradeNo,
        "goodsName": goodsName,
        "memo": memo,
        "location": location
    }, function (resp) {
        if (resp.code === '0001') {
            $('#dialog_edit_save').css({"display": "none"});
            $('#dialog_edit').css({"display": "block"});
            $('#dialog_goodsName').attr("readonly", "readonly");
            $('#dialog_memo').attr("readonly", "readonly");
            $('#dialog_location').attr("readonly", "readonly");
            reload(generateData());
        } else {
            // console.log("error" + JSON.stringify(resp));
        }
    }, true);
}

function doFilter() {
    startDate = $('#startDate').val();
    endDate = $('#endDate').val();
    content = $('#content').val();
    clearPageNo();
    reload(generateData());
}

function clearFilter() {
    startDate = "";
    endDate = "";
    content = "";

    $('#startDate').val("");
    $('#endDate').val("");
    $('#startDateDisplay').val("");
    $('#endDateDisplay').val("");
    $('#content').val("");
    clearPageNo();
    reload(generateData());
}

function toggleMoneyOrderByAndSort() {
    toggleSort();
    orderBy = "money";
    reload(generateData())
}

function toggleTimeOrderByAndSort() {
    toggleSort();
    orderBy = "createTime";
    reload(generateData())
}

function toggleSort() {
    if (sort === "desc" || sort === "") {
        sort = "asc"
    } else {
        sort = "desc"
    }
}

function clearPageNo() {
    pageNo = 0;
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

function getDisplayStr(source) {
    if (typeof source === 'undefined' || source === '' || source === "NaN") {
        return ' ';
    } else {
        return source;
    }
}

function getDisplayNum(source) {
    if (typeof source === 'undefined' || source === '' || source === "NaN") {
        return '0.00';
    } else {
        return source;
    }
}

function getDisplayWeekStr(source) {
    if (typeof source === 'undefined' || source === '') {
        return ' ';
    } else {
        return new Date(source).pattern('yyyy-MM-dd EE');
    }
}


function initTableCheckbox() {
    var $thr = $('#table_div').find('thead tr');
    var $checkAllTh = $('#checkAll');

    /*“全选/反选”复选框*/
    var $checkAll = $thr.find('input');
    $checkAll.click(function (event) {
        /*将所有行的选中状态设成全选框的选中状态*/
        $tbr.find('input').prop('checked', $(this).prop('checked'));
        /*并调整所有选中行的CSS样式*/
        if ($(this).prop('checked')) {
            $tbr.find('input').parent().parent().addClass('warning');
        } else {
            $tbr.find('input').parent().parent().removeClass('warning');
        }
        /*阻止向上冒泡，以防再次触发点击操作*/
        event.stopPropagation();
    });
    /*点击全选框所在单元格时也触发全选框的点击操作*/
    $checkAllTh.click(function () {
        $(this).find('input').click();
    });
    var $tbr = $('#billList').find('tr');
    var $checkItemTd = $('<td><input type="checkbox" name="checkItem" /></td>');
    /*每一行都在最前面插入一个选中复选框的单元格*/
    $tbr.prepend($checkItemTd);
    $tbr.eq($tbr.length - 1).find('td').eq(0).html("");
    /*点击每一行的选中复选框时*/
    $tbr.find('input').click(function (event) {
        /*调整选中行的CSS样式*/
        $(this).parent().parent().toggleClass('warning');
        /*如果已经被选中行的行数等于表格的数据行数，将全选框设为选中状态，否则设为未选中状态*/
        $checkAll.prop('checked', $tbr.find('input:checked').length == $tbr.find('input').length ? true : false);
        /*阻止向上冒泡，以防再次触发点击操作*/
        event.stopPropagation();
    });
    /*点击每一行时也触发该行的选中操作*/
    // $tbr.click(function () {
    //     $(this).find('input').click();
    // });
}

function calculateAmount() {
    var $tbr = $('#billList').find('tr');
    var inputList = $tbr.find('input:checked');
    var sum = 0.0;
    for (var i = 0; i < inputList.length; i++) {
        sum += parseFloat(inputList.eq(i).parent().siblings().eq(6).text());
    }
    $('#sum').text(sum.toFixed(2));
}

function toggleHiddenChecked() {
    var $tbr = $('#billList').find('tr');
    var inputList = $tbr.find('input:checked');
    for (var i = 0; i < inputList.length; i++) {
        var $parent = inputList.eq(i).parent();
        var tradeNo = $parent.siblings().eq(0).text();
        var nowStatus = $parent.siblings().eq(10).text();
        toggleHiddenAll(tradeNo, nowStatus);
    }
    reload(generateData());

}

function toggleHiddenAll(tradeNo, nowStatus) {
    ajaxRequest("/bootDemo/toggle/hide", "get", {
        "tradeNo": tradeNo,
        "nowStatus": nowStatus
    }, function (response) {
        if (response.msg === "成功") {
            // console.log(JSON.stringify(response));
        }
    }, true);
}

function toggleDeletedChecked() {
    var $tbr = $('#billList').find('tr');
    var inputList = $tbr.find('input:checked');
    for (var i = 0; i < inputList.length; i++) {
        var $parent = inputList.eq(i).parent();
        var tradeNo = $parent.siblings().eq(0).text();
        var nowStatus = $parent.siblings().eq(10).text() === '已删除' ? 1 : 0;
        toggleDeletedAll(tradeNo, nowStatus);
    }
    reload(generateData());
}

function toggleDeletedAll(tradeNo, nowStatus) {
    ajaxRequest("/bootDemo/delete", "get", {
        "tradeNo": tradeNo,
        "nowStatus": nowStatus
    }, function (response) {
        if (response.msg === "成功") {
            // console.log(JSON.stringify(response));
        }
    }, true);
}

function addBill() {
    $('#add_bill_dialog').modal("show");
}

$(function () {
    $('#time').val('12:00:00');
});

$('#time').on('change', function () {
    var $input = $("#time");
    var value = $input.val();
    var targetVal = value.replace(/(\d{2}):?(\d{2}):?(\d{2}).*/, "$1:$2:$3");
    $input.val(targetVal);
});

$("#insertRecord").click(function () {
    var createTime = $('#tradeDate').val() + " " + $('#time').val();
    var location = $('#location').val();
    var money = $('#money').val();
    var target = $('#target').val();
    var memo = $('#memo').val();
    var orderType = $('#orderType').val();
    var inOutType = $('#inOutType').val();
    var data = {
        "createTime": createTime,
        "location": location,
        "money": money,
        "target": target,
        "memo": memo,
        "orderType": orderType,
        "inOutType": inOutType
    };
    // console.log(JSON.stringify(data));
    ajaxRequest("/bootDemo/record/put", "get", data, function (response) {
        if (response.code === '0001') {
            alert("保存成功");
            clearInput();
        } else {
            alert("保存失败");
            // console.log(JSON.stringify(response));
        }
    }, true);
});

function clearInput() {
//        $('#tradeDate').val('');
    $('#location').val('');
    $('#money').val('');
    $('#target').val('');
    $('#memo').val('');
    $('#orderType').val('');
    $('#inOutType').val('');
}


$('#loginBtn').click(function () {
    var account = $('#loginAccount').val();
    var password = getRsaCipher($('#loginPassword').val());
    ajaxRequest("/bootDemo/user/login", "post", {"userName": account, "password": password}, function (resp) {
        if (resp) {
            if (resp.code === "0001") {
                console.log("登陆成功");
                reload(generateData());
                loadReport();
                $('#loginDialog').modal("hide");
            } else {
                alert("登陆失败");
            }
        } else {
            alert("登陆失败");
        }
    }, true);
});

$('#showRegister').click(function () {
    $('#loginDialog').modal("hide");
    $('#registerDialog').modal("show");

});

$('#registerBtn').click(function () {
    var account = $('#registerAccount').val();
    var password = $('#registerPassword').val();
    var repeatPassword = $('#passwordRepeat').val();

    if (password !== repeatPassword) {
        alert("两次密码输入不一致");
        return false;
    }
    password = getRsaCipher(password);
    ajaxRequest("/bootDemo/user/register/put", "post", {
        "userName": account,
        "password": password
    }, function (resp) {
        if (resp.code === "0001") {
            console.log("注册成功");
            $('#registerDialog').modal("hide");
        } else {
            alert(resp.msg);
        }
    }, false);
});

function isNotEmpty(val) {
    return val !== null && typeof val !== 'undefined' && val !== '';
}

layui.use('laydate', function () {
    var laydate = layui.laydate;
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
});