package org.ravic.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

/**
 * description:自定义异常处理类
 */
@ControllerAdvice
public class ExceptionHandler {
    //创建日志对象
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 异常处理
     * @param request
     * @param e
     * @return mv
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception{

        //将异常输入到日志
        logger.error("Request URL : "+request.getRequestURL()+" , Exception : "+e);
        e.printStackTrace();

        //对于自定义的带状态码的异常,
        //异常处理类不去处理,而是直接抛出,交由springboot来处理
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null)
        {
            throw e;
        }

        ModelAndView mv = new ModelAndView("error/error");
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        return mv;
    }
}
