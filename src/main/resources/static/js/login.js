$(function () {
    $(".ui.form").form({
        fields : {
            username : {
                identifier : 'username',
                rules : [
                    {
                        type : 'empty',
                        prompt : '用户名不能为空'
                    }
                ]
            },
            password : {
                identifier : 'password',
                rules : [
                    {
                        type : 'empty',
                        prompt : '密码不能为空'
                    }
                ]
            }
        }
    });
});