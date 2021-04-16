package org.ravic.blog.controller;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.service.Blog.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 归档控制层
 */
@Controller
@RequestMapping("archives")
public class ArchivesController {
    @Autowired
    private BlogServiceImpl blogService;

    /**
     * 跳转到归档显示页面
     * 渲染页面需要的数据有：所有博客的简略信息,博客的总数量
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView archives(){
        List<List<Blog>> yearList = blogService.getAllPublishedBlog();
        //发表博客总数目
        int count = blogService.getCountAllPublishedBlog();
        ModelAndView mv = new ModelAndView("archives");
        mv.addObject("yearList",yearList);
        mv.addObject("count",count);
        return mv;
    }
}
