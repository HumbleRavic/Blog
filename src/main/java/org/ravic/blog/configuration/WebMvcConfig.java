package org.ravic.blog.configuration;

import org.ravic.blog.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springMVC配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //管理员认证拦截器
    @Autowired
    private AdminInterceptor adminInterceptor;

    /**
     * 将已经放入IOC容器的自定义拦截器Bean注册
     * 拦截请求路径为/admin/**的请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin","/admin/login");
    }
}
