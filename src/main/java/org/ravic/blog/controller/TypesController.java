package org.ravic.blog.controller;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.entity.TypeAndNum;
import org.ravic.blog.page.Page;
import org.ravic.blog.page.SearchCondition;
import org.ravic.blog.service.Blog.impl.BlogServiceImpl;
import org.ravic.blog.service.Type.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 博客分类页的控制层
 */
@Controller
@RequestMapping("type")
public class TypesController {
    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private TypeServiceImpl typeService;

    /**
     * 跳转到分类页面的控制层方法,以及下一页上一页还有点击某个分类处理
     * @param condition 复合显示条件
     * @param page 默认情况下为1,当前页码
     * @return 需要渲染所有分类以及分类下博客数目,根据条件筛选出来的所有博客
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView types(SearchCondition condition,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        //当首次跳转主页时,前端不会传递condition
        if(condition == null){
            condition = new SearchCondition();
        }
        //首页只能显示已经处于发表状态的博客
        condition.setPublished(true);
        ModelAndView mv = new ModelAndView("types");
        int pageSize = 6;
        Page<Blog> blogPage = blogService.listBlog(condition,page,pageSize);
        //向页面中传递一页博客数据
        mv.addObject("blogPage",blogPage);
        //设置typeSize=-1可以显示所有的TypeAndNum
        int typeSize = -1;
        List<TypeAndNum> typeAndNumList = typeService.listTypeTop(typeSize);

        if(condition.getTypeId()!=null){
            //获取选中的typeId
            Long typeId = condition.getTypeId();
            //根据获取到的typeId在typeAndNumList中将相应的type的selected设置为true
            for(TypeAndNum typeAndNum : typeAndNumList){
                if(typeAndNum.getId()==typeId){
                    typeAndNum.setSelected(true);
                }
            }
        }

        mv.addObject("typeAndNumList",typeAndNumList);
        return mv;
    }
}
