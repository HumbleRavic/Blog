package org.ravic.blog.controller;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.entity.TagAndNum;
import org.ravic.blog.page.Page;
import org.ravic.blog.page.SearchCondition;
import org.ravic.blog.service.Blog.impl.BlogServiceImpl;
import org.ravic.blog.service.Tag.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("tag")
public class TagsController {
    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private TagServiceImpl tagService;

    /**
     * 跳转到标签页面的控制层方法,以及下一页上一页还有点击某个标签处理
     * @param condition 复合显示条件
     * @param page 默认情况下为1,当前页码
     * @return 需要渲染所有标签以及标签下博客数目,根据条件筛选出来的所有博客
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView tags(SearchCondition condition,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        //当首次跳转主页时,前端不会传递condition
        if(condition == null){
            condition = new SearchCondition();
        }
        //首页只能显示已经处于发表状态的博客
        condition.setPublished(true);
        ModelAndView mv = new ModelAndView("tags");
        int pageSize = 6;
        Page<Blog> blogPage = blogService.listBlog(condition,page,pageSize);
        //向页面中传递一页博客数据
        mv.addObject("blogPage",blogPage);
        //设置tagSize=-1可以显示所有的tagAndNum
        int tagSize = -1;
        List<TagAndNum> tagAndNumList = tagService.listTagTop(tagSize);

        if(condition.getTagId()!=null){
            //获取选中的tagId
            Long tagId = condition.getTagId();
            //根据获取到的tagId在tagAndNumList中将相应的tag的selected设置为true
            for(TagAndNum tagAndNum : tagAndNumList){
                if(tagAndNum.getId()==tagId){
                    tagAndNum.setSelected(true);
                }
            }
        }

        mv.addObject("tagAndNumList",tagAndNumList);
        return mv;
    }
}
