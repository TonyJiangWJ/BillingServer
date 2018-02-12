/**
 * Created by TonyJiang on 2017/6/28.
 */
function manageTags() {
    // $('#billTagDialog').modal("hide");
    loadTags();
    $('#manageTagsDialog').modal("show");

}
$(function () {
    $('#btnAddTag').click(function () {
        var tagName = $('#tagName').val();
        if (tagName !== 'undefined' && tagName !== '') {
            ajaxRequest("/bootDemo/tag/put", "get", {"tagName": tagName}, function (resp) {
                if (resp.code === '0001') {
//                            alert("添加成功");
                    $('#tagName').val('');
                    loadTags();
                } else {
                    alert("添加失败" + resp.msg);
                }
            }, true);
        }
    });

});

$(document).delegate("#tags > li > button", 'dblclick', function (event) {
    var tagId = $(this).attr("data-index");
    console.log(tagId);
    if (confirm("是否删除？")) {
        ajaxRequest("/bootDemo/tag/delete", "get", {"tagId": tagId}, function (resp) {
            if (resp.code === "0001") {
                loadTags();
            } else {
                alert("删除失败," + resp.msg);
            }
        }, true);
    }
});

function loadTags() {
    ajaxRequest("/bootDemo/tag/list", "get", null, function (resp) {
        if (resp.code === "0001") {
            var $tags = $('#tags');
            $tags.html("");
            var tagList = resp.tagInfoList;
            for (var i = 0; i < tagList.length; i++) {
                $tags.append(generateLi(tagList[i]));
            }
        }
        console.log(JSON.stringify(resp));
    }, true);
}
function generateTagsLi(tagInfo) {
    return '<li class="t-li"><button class="btn btn-default" data-index="' + tagInfo.tagId + '">' + tagInfo.tagName + '</button></li>';
}
