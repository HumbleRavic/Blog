package org.ravic.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 关于我控制层
 */
@Controller
@RequestMapping("about")
public class AboutController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String about(){
        return "about";
    }
}
