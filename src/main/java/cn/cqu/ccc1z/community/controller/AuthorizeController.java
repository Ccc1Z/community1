package cn.cqu.ccc1z.community.controller;

import cn.cqu.ccc1z.community.dto.AccessTokenDTO;
import cn.cqu.ccc1z.community.dto.GithubUser;
import cn.cqu.ccc1z.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        //System.out.println(user.getName()+" "+user.getId());
        if(user != null){
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",user);//相当于银行账户创建成功
            return "redirect:/";

        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
        //登录成果后返回主页
    }
}
