$(function () {

    //allsuit
    //在移动端闭合或者开启顶部菜单
    $("#showMenuButton").on("click",function () {
        $(".togglemenu").toggleClass("m-mobile-hide");
    });

    //blog.html
    //打赏按钮弹出二维码提示
    $(".payButton").popup({
       popup : $(".payQR.popup"),
        on : 'click',
        position : 'bottom center'
    });

    //about.html
    //github按钮弹出二维码
    $(".githubButton").popup({
        popup : $(".githubQR.popup"),
        position : 'bottom center'
    });

    //about.html
    //qq按钮弹出二维码
    $(".qqButton").popup({
        popup : $(".qqQR.popup"),
        position : 'bottom center'
    });

    //about.html
    //wechat按钮弹出二维码
    $(".wechatButton").popup({
        popup : $(".wechatQR.popup"),
        position : 'bottom center'
    });

    //blog.html
    //wechat按钮弹出二维码
    $(".wechatButton1").popup({
        popup : $(".wechatQR1.popup"),
        on : "click",
        position : 'left center'
    });

    //blog.html
    //目录按钮弹出目录
    $(".catalogButton").popup({
        popup : $(".catalog.popup"),
        on : "click",
        position : 'left center'
    });

    //blog.html
    //生成二维码
    var qrcode = new QRCode("qrcode", {
        text: "http://jindo.dev.naver.com/collie",
        width: 120,
        height: 120,
        colorDark : "#000000",
        colorLight : "#ffffff",
        correctLevel : QRCode.CorrectLevel.H
    });

    //blogs blogs-input
    //下拉菜单功能初始化
    $(".ui.dropdown").dropdown();

    //blog.html
    //平滑滚动
    $("#toTopButton").on("click",function () {
        $(window).scrollTo("0%",500);
    });
    $("#toCommentButton").on("click",function () {
         $(window).scrollTo($("#comment"),500);
    });

    //blogs-input.html
    //表单提交校验
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
            tag : {
                identifier : 'tag',
                rules : [
                    {
                        type : 'empty',
                        prompt : '标签不能为空'
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

    //blog.html
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

    //blogs-input.html
    var contentEditor;
    //初始化MarkDown插件
    contentEditor = editormd("md-content",{
        width : "100%",
        height : 640,
        syncScrolling : "single",
        path    : "../editor/lib/"
    });
});