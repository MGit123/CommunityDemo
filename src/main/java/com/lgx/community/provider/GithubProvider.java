package com.lgx.community.provider;

import com.alibaba.fastjson.JSON;
import com.lgx.community.dto.AccessTokenDTO;
import com.lgx.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author admin
 * @date 2019/8/22 12:16
 * 提供Github第三发授权
 */

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

         MediaType mediaType= MediaType.get("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();

         RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
         Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string= response.body().string();
                String token=string.split("&")[0].split("=")[1];
                return token;
        }catch (Exception e){
                e.printStackTrace(); //抛出异常
            }

            return null;
    }

    public GithubUser getUser(String accessToken){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
        System.err.println("githubuser:"+GithubUser.class);

        try {
                Response response = client.newCall(request).execute();
                String string= response.body().string();
                System.err.println("string:"+string);
                GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
                return githubUser;
            }catch (IOException e){
            }
        return null;
    }

}
