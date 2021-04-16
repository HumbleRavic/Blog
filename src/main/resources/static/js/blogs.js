$(function () {

    //初始化checkbox
    $('.ui.checkbox').checkbox();

    //前端校验,校验标题和分类
    //由于使用ajax,不点击提交按钮,应该不会进行semanticUI自带的前端校验
    $(".ui.form").form({
        fields : {
            title : {
                identifier : 'title',
                rules : [
                    {
                        type : 'empty',
                        prompt : '标题不能为空'
                    }
                ]
            },
            typeId : {
                identifier : 'typeId',
                rules : [
                    {
                        type : 'empty',
                        prompt : '分类不能为空'
                    }
                ]
            }
        }
    });

    //清空按钮清空标签
    $("#clear-btn").on("click",function () {
        $(".ui.type.dropdown").dropdown('clear');
    });
});

//上一页（下一页）点击方法处理
//将上一页（下一页）dom对象中数据域中的页码放到form中的隐藏域中,并触发表单提交
function page(source) {
    $("[name='page']").val($(source).data("page"));
    loadData();
}

//Ajax请求发送搜索条件,返回局部片段
function loadData() {
    $("#table-container").load("/admin/blogs/search",{
        page : $("[name='page']").val(),
        typeId : $("[name='typeId']").val(),
        recommend : $("[name='recommend']").prop("checked"),
        title : $("[name='title']").val()
    });
}

//搜索点击事件处理
function searchByCondition() {
    //由于该表单没有submit按钮,而使用普通按钮触发ajax请求,因此需要显示调用semanticUI前端校验方法
    var boo = $(".ui.form").form('validate form');
    if(boo){
        $("[name='page']").val('1');
        loadData();
    }else{
        alert("前端校验失败");
    }
}