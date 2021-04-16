package org.ravic.blog.interceptor;

import org.ravic.blog.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义进行管理员身份认证的拦截器
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    /**
     * 检验请求所在的会话session对象中是否有username=root
     * 有则放行,没有则拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //判断当前会话的session中有没有username,并且username要为root
        if (user != null) {
            if (user.getUsername().equals("root")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
