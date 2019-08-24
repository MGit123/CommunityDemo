package com.lgx.community.controller;

import com.lgx.community.dto.AccessTokenDTO;
import com.lgx.community.dto.GithubUser;
import com.lgx.community.entity.User;
import com.lgx.community.mapper.UserMapper;
import com.lgx.community.provider.GithubProvider;
import com.lgx.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author admin
 * @date 2019/8/21 19:02
 */

@Controller
public class BaseController {

    @Autowired
    private GithubProvider githubProvider;



    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecrct;

    @Value("${github.redirect.url}")
    private String redirectUrl;

   @GetMapping("/")
    public String index(HttpServletRequest request){

       Cookie[] cookies=request.getCookies();
       for(Cookie cookie:cookies){
           if(cookie.getName().equals("token")){
               String token=cookie.getValue();
               User user=userService.findByToken(token);
               if(user!=null){
                   System.err.println("user:"+user);
                   request.getSession().setAttribute("user",user);
               }
               break;
           }
       }

       return "index";
   }

   @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response){

       AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
       accessTokenDTO.setClient_id(clientId);
       accessTokenDTO.setClient_secret(clientSecrct);
       accessTokenDTO.setCode(code);
       accessTokenDTO.setState(state);
       accessTokenDTO.setRedirect_url(redirectUrl);
       String accessToken=githubProvider.getAccessToken(accessTokenDTO);

       //获取user
       GithubUser githubUser=githubProvider.getUser(accessToken);
       System.err.println("username:"+githubUser.getLogin());

       if(githubUser!=null){
           User user=new User();
           user.setName(githubUser.getLogin());
           user.setAccountId(String.valueOf(githubUser.getId()));
           String token=UUID.randomUUID().toString();
           user.setToken(token);
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           user.setAvatarUrl(githubUser.getAvatar_url());
           userService.insert(user);

           //request.getSession().setAttribute("user",githubUser);
           response.addCookie(new Cookie("token",token));
           return "redirect:/";
       }else{
           return "redirect:/";
       }
   }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String publishInfo(){
       return "/";
    }


}
