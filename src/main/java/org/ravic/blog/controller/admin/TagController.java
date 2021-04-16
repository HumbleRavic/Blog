package org.ravic.blog.controller.admin;

import org.ravic.blog.entity.Tag;
import org.ravic.blog.page.Page;
import org.ravic.blog.service.Tag.impl.TagServiceImpl;
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
public class TagController {

    private static final String INPUT = "admin/tags-input";
    private static final String LIST = "admin/tags";
    private static final String REDIRECT_LIST = "redirect:/admin/tags";

    @Autowired
    private TagServiceImpl tagService;

    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public ModelAndView tags(@RequestParam(value = "page",required = false,defaultValue = "1") int page){
        int size = 8;
        //分页管理页
        Page<Tag> tagPage = tagService.listTag(page,size);
        ModelAndView mv = new ModelAndView(LIST);
        mv.addObject("tagPage",tagPage);
        return mv;
    }

    @RequestMapping(value = "tags/input", method = RequestMethod.GET)
    public String add(){
        return INPUT;
    }

    @RequestMapping(value = "tags/edit", method = RequestMethod.GET)
    public ModelAndView edit(Long id){
        Tag tag = tagService.getTag(id);
        ModelAndView mv = new ModelAndView(INPUT);
        mv.addObject("tag",tag);
        return mv;
    }

    @RequestMapping(value = "tags",method = RequestMethod.POST)
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //后端校验
        if(result.hasErrors()){
            //记录
            logError(result);
            //返回后端校验提示
            attributes.addFlashAttribute("validmessage","后端校验失败");
            //后端校验失败跳转新增分类页面
            return "redirect:/admin/tags-input";
        }
        else{
            //根据前端传过来的id判断是新增还是编辑
            if (tag.getId() == -1) {
                //新增
                return addTag(tag, attributes);
            }
            {
                //编辑
                return editTag(tag, attributes);
            }
        }
    }

    @RequestMapping(value = "tags/delete", method = RequestMethod.GET)
    public String delete(Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("delmessage","删除标签成功");
        return REDIRECT_LIST;
    }

    public void logError(BindingResult result){
        List<String> errors = new ArrayList<>();
        for(ObjectError error : result.getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
    }

    public String addTag(Tag tag, RedirectAttributes attributes){
        //将新增的标签创建Tag对象,写入数据库
        boolean flag = tagService.saveTag(tag);
        //判断是否添加成功标签
        if(flag){
            //给标签页面传递添加成功的消息
            attributes.addFlashAttribute("addmessage","新增标签成功!");
            //重定向到标签页面
            return REDIRECT_LIST;
        }
        else{
            attributes.addFlashAttribute("message","添加标签失败!");
            //重定向到添加标签页面
            return "redirect:/admin/tags/input";
        }
    }

    public String editTag(Tag tag, RedirectAttributes attributes){
        tagService.updateTag(tag);
        //给标签页面传递编辑成功的消息
        attributes.addFlashAttribute("message","编辑标签成功!");
        //重定向到标签页面
        return REDIRECT_LIST;
    }
}
