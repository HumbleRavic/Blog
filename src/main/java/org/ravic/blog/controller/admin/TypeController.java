package org.ravic.blog.controller.admin;

import org.ravic.blog.entity.Type;
import org.ravic.blog.page.Page;
import org.ravic.blog.service.Type.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class TypeController {

    private static final String INPUT = "admin/types-input";
    private static final String LIST = "admin/types";
    private static final String REDIRECT_LIST = "redirect:/admin/types";

    @Autowired
    private TypeServiceImpl typeService;

    /**
     * 跳转至分类管理页
     * @param page 当前页码,不必须默认值为1,是因为当用户直接访问分类页面，肯定显示的是第一页
     * @return
     */
    @RequestMapping(value = "types", method = RequestMethod.GET)
    public ModelAndView types(@RequestParam(value = "page",required = false,defaultValue = "1") int page){
        int size = 8;
        //分页管理页
        Page<Type> typePage = typeService.listType(page,size);
        ModelAndView mv = new ModelAndView(LIST);
        mv.addObject("typePage",typePage);
        return mv;
    }

    /**
     * 跳转到新增页面
     */
    @RequestMapping(value = "types/input", method = RequestMethod.GET)
    public String add(){
        return INPUT;
    }

    /**
     * 跳转到编辑页面
     * @param id 所要编辑分类的id
     * @return 预先设置好该id的分类对象的name
     */
    @RequestMapping(value = "types/edit", method = RequestMethod.GET)
    public ModelAndView edit(Long id){
        Type type = typeService.getType(id);
        ModelAndView mv = new ModelAndView(INPUT);
        mv.addObject("type",type);
        return mv;
    }

    /**
     * 新增页面提交数据,注意区别于上面同样value但是为GET的控制层方法
     * @param type 前端传递过来的数据自动组装成Type实体对象
     * @param result 后端校验如果校验出错,会将错误给result对象
     * @return
     */
    @RequestMapping(value = "types",method = RequestMethod.POST)
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //后端校验
        if(result.hasErrors()){
            //记录
            logError(result);
            //返回后端校验提示
            attributes.addFlashAttribute("validmessage","后端校验失败");
            //后端校验失败跳转新增分类页面
            return "redirect:/admin/types-input";
        }
        else{
            //根据前端传过来的id判断是新增还是编辑
            if (type.getId() == -1) {
                //新增
                return addType(type, attributes);
            }
            {
                //编辑
                return editType(type, attributes);
            }
        }
    }

    /**
     * 删除分类
     * @param id
     * @param attributes
     * @return
     */
    @RequestMapping(value = "types/delete", method = RequestMethod.GET)
    public String delete(Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("delmessage","删除分类成功");
        return REDIRECT_LIST;
    }

    /**
     * 将后端校验的错误先存放在List中
     * @param result 后端校验错误结果
     */
    public void logError(BindingResult result){
        List<String> errors = new ArrayList<>();
        for(ObjectError error : result.getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
    }

    /**
     * 新增分类
     * @param type 新增的Type对象
     * @param attributes
     * @return
     */
    public String addType(Type type, RedirectAttributes attributes){
        //将新增的分类创建Type对象,写入数据库
        boolean flag = typeService.saveType(type);
        //判断是否添加成功分类
        if(flag){
            //给分类页面传递添加成功的消息
            attributes.addFlashAttribute("addmessage","新增分类成功!");
            //重定向到分类页面
            return REDIRECT_LIST;
        }
        else{
            attributes.addFlashAttribute("message","添加分类失败!");
            //重定向到添加分类页面
            return "redirect:/admin/types/input";
        }
    }

    /**
     * 编辑分类
     * @param type 编辑的Type对象
     * @param attributes
     * @return
     */
    public String editType(Type type, RedirectAttributes attributes){
        typeService.updateType(type);
        //给分类页面传递编辑成功的消息
        attributes.addFlashAttribute("message","编辑分类成功!");
        //重定向到分类页面
        return REDIRECT_LIST;
    }
}
