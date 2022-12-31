package com.fijimf.uberscraper.site;

import org.checkerframework.common.reflection.qual.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
