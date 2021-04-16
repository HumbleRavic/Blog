package org.ravic.blog.controller;

import org.ravic.blog.configuration.SourceConfig;
import org.ravic.blog.entity.Blog;
import org.ravic.blog.entity.Comment;
import org.ravic.blog.entity.TagAndNum;
import org.ravic.blog.entity.TypeAndNum;
import org.ravic.blog.page.Page;
import org.ravic.blog.page.SearchCondition;
import org.ravic.blog.service.Blog.impl.BlogServiceImpl;
import org.ravic.blog.service.Comment.impl.CommentServiceImpl;
import org.ravic.blog.service.Tag.impl.TagServiceImpl;
import org.ravic.blog.service.Type.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页访问控制层
 */
@Controller
public class IndexController {
    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private TypeServiceImpl typeService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private SourceConfig sourceConfig;

    @Autowired
    private CommentServiceImpl commentService;

    /**
     * 跳转到主页显示页面
     * 跳转到主页面时会携筛选博客条件SearchCondition对象
     * 第一次到主页时处于第一页
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index(SearchCondition condition,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        //当首次跳转主页时,前端不会传递condition
        if(condition == null){
            condition = new SearchCondition();
        }
        //首页只能显示已经处于发表状态的博客
        condition.setPublished(true);
        ModelAndView mv = new ModelAndView("index");
        int pageSize = 6;
        Page<Blog> blogPage = blogService.listBlog(condition,page,pageSize);
        //向页面中传递一页博客数据
        mv.addObject("blogPage",blogPage);
        //向页面中传递所有分类（分类名+分类数目）
        int typeSize = 6;
        List<TypeAndNum> typeAndNumList = typeService.listTypeTop(typeSize);
        mv.addObject("typeAndNumList",typeAndNumList);
        //向页面中传递所有标签（标签名+标签数目）
        int tagSize = 6;
        List<TagAndNum> tagAndNumList = tagService.listTagTop(tagSize);
        mv.addObject("tagAndNumList",tagAndNumList);
        //向页面传递推荐博客
        int blogSize = 6;
        List<Blog> blogList = blogService.listBlogTop(blogSize);
        mv.addObject("blogList",blogList);
        //向页面传递一个日期格式化对象
        mv.addObject("format",new SimpleDateFormat("yyyy-MM-dd"));
        return mv;
    }

    /**
     * 根据关键字搜索已发布状态的博客标题的名称
     * 分页查询
     * @param query 搜素关键字
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(String query,@RequestParam(value = "page",required = false,defaultValue = "1") int page){
        //将分页查询的条件封装
        SearchCondition condition = new SearchCondition();
        condition.setLikeTitle(query);
        condition.setPublished(true);
        int size = 6;
        Page<Blog> blogPage = blogService.listBlog(condition,page,6);
        ModelAndView mv = new ModelAndView("search");
        //向前端传递搜素分页结果
        mv.addObject("blogPage",blogPage);
        //向页面传递一个日期格式化对象
        mv.addObject("format",new SimpleDateFormat("yyyy-MM-dd"));
        return mv;
    }

    /**
     * 根据blog的id跳转到该id的blog的详情页
     * 需要向其页面传递：Uer（avatar nickname）blog（title content....  blog对应的type,tag,commend
     * 每次访问博客详情页面,都需要根据blogId在blog数据表给相应的blog的views+1
     * @param id blog的id
     * @return
     */
    @RequestMapping(value = "blog", method = RequestMethod.GET)
    public ModelAndView blog(Long id){
        //将数据库中存储的MarkDown编辑的内容转换成html内容
        Blog blog = blogService.getConvertBlog(id);
        ModelAndView mv = new ModelAndView("blog");
        //向前端页面传递博客实体对象
        mv.addObject("blog",blog);
        //向页面传递一个日期格式化对象
        mv.addObject("format",new SimpleDateFormat("yyyy-MM-dd"));
        //向页面传递固定资源路径配置对象
        mv.addObject("sourceConfig",sourceConfig);
        //向页面传递空的commentList,防止评论控制层方法传递的commentList无法被thymeleaf识别
        List<Comment> commentList = new ArrayList<>();
        mv.addObject("commentList",commentList);
        return mv;
    }
}
