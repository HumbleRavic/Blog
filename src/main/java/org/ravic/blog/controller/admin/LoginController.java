package org.ravic.blog.controller.admin;

import org.ravic.blog.entity.User;
import org.ravic.blog.service.User.impl.UserServiceImpl;
import org.ravic.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录页控制器
 */
@Controller
@RequestMapping("admin")
public class LoginController {
    //登录功能service层对象
    @Autowired
    private UserServiceImpl userService;

    /**
     * 跳转到登录页
     * 如果用户已经登陆,那么在当前会话中如果再次到登录页面会直接跳转到index
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null&&user.getUsername().equals("root")){
            return "admin/index";
        }else{
            return "admin/login";
        }

    }

    /**
     * 使用用户名和密码登录
     * 进行后端校验,校验用户名不能为空
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@Valid User user, BindingResult result, HttpSession session,
                        RedirectAttributes attributes){
        if(result.hasErrors()){
            //将后端校验的错误先存在list中
            List<String> errors = new ArrayList<>();
            for(ObjectError error : result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            System.out.println("----------"+errors);
            //返回后端校验提示
            attributes.addFlashAttribute("validmessage","后端校验失败");
            //后端校验失败跳转新增分类页面
            return "redirect:/admin";
        }
        else{
            //根据客户端输入的用户名和密码,判断是否存在该用户
            //将密码利用MD5加密
            User userResult = userService.checkUser(user.getUsername(), MD5Util.encodeByMD5(user.getPassword()));
            if(userResult != null){
                //将user对象中password清空,防止泄露
                userResult.setPassword(null);
                //将当前会话的user对象放到session中,方便在同一次会话中使用
                session.setAttribute("user",userResult);
                return "admin/index";
            }
            else {
                attributes.addFlashAttribute("message","用户名或密码错误");
                //重定向跳转至登录页面
                return "redirect:/admin";
            }
        }
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //清空当前会话session中user信息
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
