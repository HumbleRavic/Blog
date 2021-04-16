package org.ravic.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 使用AOP进行日志处理
 */
@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //定义切点,切点为org.ravic.blog.controller包下所有控制类中所有的方法
    @Pointcut("execution(* org.ravic.blog.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void testBefore(JoinPoint jp){
        //获取当前线程中的HttpServletRequest对象
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

        //将请求的信息输入到日志
        logger.info("request : "+requestLog.toString());
    }

    @AfterReturning(pointcut = "log()", returning = "returningValue")
    public void testAfterReturning(Object returningValue){
        logger.info("result : "+returningValue);
    }

    //存放客户端请求信息的数据结构
    private class RequestLog{
        //请求的url
        private String url;
        //请求来自的ip地址
        private String ip;
        //请求交由哪个方法处理
        private String classMethod;
        //请求向该方法传递的参数
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
