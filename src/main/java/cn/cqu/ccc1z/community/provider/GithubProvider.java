package cn.cqu.ccc1z.community.provider;

import cn.cqu.ccc1z.community.dto.AccessTokenDTO;
import cn.cqu.ccc1z.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//import okhttp3.*;


/**
 * Created by Ccc1Z on 2019/03/22
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        /**
         * OkHttp的post请求代码
         */
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String[] split = str.split("&");
            String token = split[0].split("=")[1];
            //String token = tokenstr.split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过accesstoken拿到用户信息
     * @return
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //把JOSN对象的string转换成java的Object对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
