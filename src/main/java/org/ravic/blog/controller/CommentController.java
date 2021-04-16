package org.ravic.blog.controller;

import org.ravic.blog.entity.Comment;
import org.ravic.blog.entity.User;
import org.ravic.blog.service.Comment.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 提交评论的控制层
 */
@Controller
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    /**
     * 只负责显示评论列表
     * @return
     */
    @RequestMapping(value = "comments", method = RequestMethod.GET)
    public ModelAndView comments(Long blogId){
        ModelAndView mv = new ModelAndView("blog :: commentlist");
        //从数据库中获取comment中blogId所对应的所有评论
        List<Comment> commentList = commentService.listCommentByBlogId(blogId);
        mv.addObject("commentList",commentList);
        return mv;
    }

    /**
     * 接收前端传递的评论请求,前端使用Ajax请求,只负责存储评论,之后重定向到显示评论列表的控制层方法
     * @param comment 前端提交的评论对象
     * @return
     */
    @RequestMapping(value = "comments", method = RequestMethod.POST)
    public String post(Comment comment, HttpSession session){
        //判断该请求所在的会话身份是否为管理员
        User user = (User)session.getAttribute("user");
        if(user!=null){
            if(user.getUsername().equals("root")){
                //为comment对象设置admin为true
                comment.setAdmin(true);
            }
            else{
                comment.setAdmin(false);
            }
        }
        else{
            comment.setAdmin(false);
        }
        //先将前端提交的评论存储
        commentService.saveComment(comment);
        Long blogId = comment.getBlogId();
        //重定向到显示方法,以及get请求的参数放入blogId
        return "redirect:/comments?blogId="+blogId;
    }
}
