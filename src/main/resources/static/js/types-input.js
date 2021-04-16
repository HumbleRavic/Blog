$(function () {
   $(".ui.form").form({
       fields : {
           name : {
               identifier : 'name',
               rules : [
                   {
                       type : 'empty',
                       prompt : '分类不能为空'
                   }
               ]
           }
       }
   });
});