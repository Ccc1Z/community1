package cn.cqu.ccc1z.community.controller;

import cn.cqu.ccc1z.community.dto.AccessTokenDTO;
import cn.cqu.ccc1z.community.dto.GithubUser;
import cn.cqu.ccc1z.community.mapper.UserMapper;
import cn.cqu.ccc1z.community.model.User;
import cn.cqu.ccc1z.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Create by Ccc1Z on 2020/03/22
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //System.out.println(user.getName()+" "+user.getId());
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setAvatarURL(githubUser.getAvatarUrl());
            userMapper.insert(user);
            //手动写入cookie
            response.addCookie(new Cookie("token",token));
            //登录成功，写cookie和session
            //request.getSession().setAttribute("user",githubUser);//相当于银行账户创建成功
            return "redirect:/";

        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
        //登录成果后返回主页

    }
}
