$(function () {
    //在移动端闭合或者开启顶部菜单
    $("#showMenuButton").on("click",function () {
        $(".togglemenu").toggleClass("m-mobile-hide");
    });

    //下拉菜单功能初始化
    $(".ui.dropdown").dropdown();
});

//搜索图标点击提交搜索表单
function searchSubmit() {
    $("#searchForm").submit();
}