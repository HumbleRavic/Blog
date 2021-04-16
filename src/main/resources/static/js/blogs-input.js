$(function () {

    //初始化checkbox
    $('.ui.checkbox').checkbox();

    //表单提交校验
    //标签可以为空,但是分类不能为空
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
            content : {
                identifier : 'content',
                rules : [
                    {
                        type : 'empty',
                        prompt : '博客内容不能为空'
                    }
                ]
            },
            type : {
                identifier : 'type',
                rules : [
                    {
                        type : 'empty',
                        prompt : '分类不能为空'
                    }
                ]
            },
            pic : {
                identifier : 'pic',
                rules : [
                    {
                        type : 'empty',
                        prompt : '首图地址不能为空'
                    }
                ]
            },
        }
    });

    //初始化MarkDown插件
    var contentEditor;
    contentEditor = editormd("md-content",{
        width : "100%",
        height : 640,
        syncScrolling : "single",
        path    : "../../editor/lib/"
    });

    //暂存事件
    $("#save-btn").on("click",function () {
        $("[name='published']").val(false);
        //显式调用semanticUI前端校验
        var boo = $(".ui.form").form('validate form');
        if(boo){
            $("#blog-table").submit();
        }else{
            alert("前端校验失败");
        }
    });

    //发布事件
    $("#publish-btn").on("click",function () {
        $("[name='published']").val(true);
        //显式调用semanticUI前端校验
        var boo = $(".ui.form").form('validate form');
        if(boo){
            $("#blog-table").submit();
        }else{
            alert("前端校验失败");
        }
    });
});