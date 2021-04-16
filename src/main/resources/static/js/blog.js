$(function () {
    //打赏按钮弹出二维码提示
    $(".payButton").popup({
        popup : $(".payQR.popup"),
        on : 'click',
        position : 'bottom center'
    });

    //wechat按钮弹出二维码
    $(".wechatButton").popup({
        popup : $(".wechatQR.popup"),
        on : "click",
        position : 'left center'
    });

    //目录按钮弹出目录
    $(".catalogButton").popup({
        popup : $(".catalog.popup"),
        on : "click",
        position : 'left center'
    });

    //平滑滚动
    $("#toTopButton").on("click",function () {
        $(window).scrollTo("0%",500);
    });
    $("#toCommentButton").on("click",function () {
        $(window).scrollTo($("#comment"),500);
    });

    //初始化目录生成器tocbot
    tocbot.init({
        // Where to render the table of contents.
        tocSelector: '.js-toc',
        // Where to grab the headings to build the table of contents.
        contentSelector: '.js-toc-content',
        // Which headings to grab inside of the contentSelector element.
        headingSelector: 'h1, h2, h3',
        // For headings inside relative or absolute positioned containers within content.
        hasInnerContainers: true,
    });

    //评论表单提交验证
    $(".ui.form").form({
        fields : {
            content : {
                identifier : 'content',
                rules : [
                    {
                        type : 'empty',
                        prompt : '评论内容不能为空'
                    }
                ]
            },
            nickname : {
                identifier : 'nickname',
                rules : [
                    {
                        type : 'empty',
                        prompt : '昵称不能为空'
                    }
                ]
            },
            email : {
                identifier : 'email',
                rules : [
                    {
                        type : 'empty',
                        prompt : '邮箱不能为空'
                    },
                    {
                        type : 'email',
                        prompt : '不是有效的电子邮箱地址'
                    }
                ]
            }
        }
    });

    //提交评论按钮事件处理
    $("#commentpost-btn").on("click",function () {
        //显示调用semanticUI前端数据校验
        var boo = $(".ui.form").form('validate form');
        //判断校验是否成功
        if(boo){
            //ajax发送请求
            $("#comment").load("/comments",{
                "cid" : $("[name='cid']").val(),
                "blogId" : $("[name='blogId']").val(),
                "nickname" : $("[name='nickname']").val(),
                "email" : $("[name='email']").val(),
                "content" : $("[name='content']").val()
            },function () {
                //提交完评论后滚动到评论
                $(window).scrollTo($("#comment"),500);
                //清空评论提交表单的数据,不要清空含有blog的id隐藏域的数据
                $("[name='cid']").val("");
                $("[name='nickname']").val("");
                $("[name='email']").val("");
                $("[name='content']").val("");
            });
        }
        else{
            alert("前端校验失败");
        }
    });

    //在页面加载完毕后就像后端提交获取评论,刷新评论区的请求
    $("#comment").load("/comments?blogId="+$("[name='blogId']").val());

    //点击回复按钮事件处理
    $("#comment").on("click",".comment .reply",function () {
        var $content = $("[name='content']");
        //在评论输入框中已占位符显示@回复人的昵称
        $content.attr("placeholder","@"+$(this).data("commentnickname"));
        //将要回复的评论的id作为要发表评论的cid放在隐藏域中
        $("[name='cid']").val($(this).data("commentid"));
        //窗口滚动到填写评论区域
        $(window).scrollTo($("#comment-form"),500);
    });
});