package org.ravic.blog.controller.admin;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.entity.Tag;
import org.ravic.blog.entity.Type;
import org.ravic.blog.entity.User;
import org.ravic.blog.page.SearchCondition;
import org.ravic.blog.page.Page;
import org.ravic.blog.service.Blog.impl.BlogServiceImpl;
import org.ravic.blog.service.Tag.impl.TagServiceImpl;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客管理页控制器
 */
@Controller
@RequestMapping("admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TypeServiceImpl typeService;
    @Autowired
    private TagServiceImpl tagService;

    /**
     * 跳转到博客管理页面
     * 第一次跳转时没有搜索条件,并且处于第一页
     * @return
     */
    @RequestMapping(value = "blogs",method = RequestMethod.GET)
    public ModelAndView blogsPage(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  SearchCondition searchCondition){
        searchCondition = new SearchCondition();
        ModelAndView mv = new ModelAndView(LIST);
        //设置页面大小为8
        int size = 8;
        Page<Blog> blogPage = blogService.listBlog(searchCondition,page,size);
        mv.addObject("blogPage",blogPage);
        //向模板页面传递日期格式化对象,方便在页面根据需要格式化Date对象
        mv.addObject("format",new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        //向模板页面中传递所有的分类
        List<Type> typeList = typeService.getAllType();
        mv.addObject("typeList",typeList);
        return mv;
    }

    /**
     * 前端使用Ajax请求,后端返回博客列表thymeleaf片段
     * 前端搜索点击,上一页,下一页都会交给这个控制层方法
     * 进行后端校验,校验标题和分类是否为空
     * @param page ajax请求获取博客的页码
     * @param searchCondition 获取博客的复合搜索条件
     * @return
     */
    @RequestMapping(value = "blogs/search", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam(required = false,defaultValue = "1") int page,
                               @Valid SearchCondition searchCondition, BindingResult result, RedirectAttributes attributes){
        //后端校验标题和分类
        if (result.hasErrors()) {
            logError(result);
            ModelAndView mv = new ModelAndView("admin/_fragments :: validmessage");
            mv.addObject("message", "标题或者分类不能为空");
            return mv;
        } else {
            //如果前端没有设置搜索条件,将condition初始化
            if (searchCondition == null) {
                searchCondition = new SearchCondition();
            }
            ModelAndView mv = new ModelAndView("admin/blogs :: bloglist");
            //设置页面大小为8
            int size = 8;
            Page<Blog> blogPage = blogService.listBlog(searchCondition, page, size);
            mv.addObject("blogPage", blogPage);
            //向模板页面传递日期格式化对象,方便在页面根据需要格式化Date对象
            mv.addObject("format", new SimpleDateFormat("yyyy-MM-dd HH:mm"));
            return mv;
        }
    }

    /**
     * 新增博客
     * 跳转至博客新增页面
     * @return
     */
    @RequestMapping(value = "blogs/add", method = RequestMethod.GET)
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView(INPUT);
        //向模板中添加默认的blog对象,防止模板引擎因为识别不了变量名,无法解析
        Blog blog = new Blog();
        mv.addObject("blog",blog);
        //将所有的标签传递到编辑页面
        List<Tag> tagList = tagService.getAllTag();
        mv.addObject("tagList",tagList);
        //将所有的分类传递到编辑页面
        List<Type> typeList = typeService.getAllType();
        mv.addObject("typeList",typeList);
        return mv;
    }

    /**
     * 编辑博客
     * 跳转至编辑博客页面
     * @param id
     * @return
     */
    @RequestMapping(value = "blogs/edit", method = RequestMethod.GET)
    public ModelAndView edit(long id){
        ModelAndView mv = new ModelAndView(INPUT);
        //根据前端传递过来的博客id获取博客
        Blog blog = blogService.getBlog(id);
        List<Long> list = new ArrayList<>();
        for(Tag tag : blog.getTagList()){
            list.add(tag.getId());
        }
        String blogTagString = list.toString().replaceAll("\\[(.*)\\]","$1").replaceAll(" ","");
        mv.addObject("blogTagString",blogTagString);
        //将博客页面传递到编辑页面
        mv.addObject("blog",blog);
        //将所有的标签传递到编辑页面
        List<Tag> tagList = tagService.getAllTag();
        mv.addObject("tagList",tagList);
        //将所有的分类传递到编辑页面
        List<Type> typeList = typeService.getAllType();
        mv.addObject("typeList",typeList);
        return mv;
    }

    /**
     * 新增编辑提交博客
     * 进行后端校验,校验内容有:标题,内容,分类,首图.但是存在暂存状态
     * @param blog 提交封装出的博客对象,有的博客可以不加标签,因此tagIds并不是必须的
     * @param tagIds 博客的标签id组成的数组
     * @return mv 携带者提示信息到博客管理页面
     */
    @RequestMapping(value = "blogs",method = RequestMethod.POST)
    public String post(@RequestParam(required = false) Integer[] tagIds, @Valid Blog blog, BindingResult result,
                       RedirectAttributes attributes, HttpSession session){
        //后端校验
        if(result.hasErrors()){
            logError(result);
            attributes.addFlashAttribute("message","后端校验失败");
            return REDIRECT_LIST;
        }
        else {
            //将会话中当前userId放入blog对象中
            User user = (User)session.getAttribute("user");
            blog.setUserId(user.getId());
            //分辨是编辑已有还是新增
            if(blog.getId()==-1){
                //新增博客
                blogService.saveBlog(blog,tagIds);
            }else{
                //编辑博客
                blogService.updateBlog(blog,tagIds);
            }
            attributes.addFlashAttribute("message","新增或编辑成功");
            return REDIRECT_LIST;
        }
    }

    /**
     * 删除指定id的博客
     * @param id 博客编号
     * @return 重定向admin/blogs
     */
    @RequestMapping(value = "blogs/delete", method = RequestMethod.GET)
    public String delete(Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    //记录校验Error
    public void logError(BindingResult result){
        List<String> errors = new ArrayList<>();
        for(ObjectError error : result.getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
        System.out.println("----------------"+errors);
    }
}
