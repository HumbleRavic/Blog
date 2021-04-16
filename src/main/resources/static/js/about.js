$(function () {
    //github按钮弹出二维码
    $(".githubButton").popup({
        popup : $(".githubQR.popup"),
        position : 'bottom center'
    });

    //qq按钮弹出二维码
    $(".qqButton").popup({
        popup : $(".qqQR.popup"),
        position : 'bottom center'
    });

    //wechat按钮弹出二维码
    $(".wechatButton").popup({
        popup : $(".wechatQR.popup"),
        position : 'bottom center'
    });
});