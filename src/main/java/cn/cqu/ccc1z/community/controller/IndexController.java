package cn.cqu.ccc1z.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Ccc1Z 2020/3/19
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){return "index";}
}
