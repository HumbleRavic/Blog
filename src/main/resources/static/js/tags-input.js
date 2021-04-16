$(function () {
    $(".ui.form").form({
        fields : {
            name : {
                identifier : 'name',
                rules : [
                    {
                        type : 'empty',
                        prompt : '标签不能为空'
                    }
                ]
            }
        }
    });
});