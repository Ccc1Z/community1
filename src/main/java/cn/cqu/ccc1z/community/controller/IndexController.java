package cn.cqu.ccc1z.community.controller;

import cn.cqu.ccc1z.community.mapper.UserMapper;
import cn.cqu.ccc1z.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ccc1Z 2020/3/19
 */
@Controller
public class IndexController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }



        return "index";}
}
