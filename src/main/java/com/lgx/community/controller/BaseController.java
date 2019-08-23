package com.lgx.community.controller;

import com.lgx.community.dto.AccessTokenDTO;
import com.lgx.community.dto.GithubUser;
import com.lgx.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 * @date 2019/8/21 19:02
 */

@Controller
public class BaseController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecrct;

    @Value("${github.redirect.url}")
    private String redirectUrl;

   @RequestMapping("/")
    public String index(){
       return "index";
   }

   @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request){

       AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
       accessTokenDTO.setClient_id(clientId);
       accessTokenDTO.setClient_secret(clientSecrct);
       accessTokenDTO.setCode(code);
       accessTokenDTO.setState(state);
       accessTokenDTO.setRedirect_url(redirectUrl);
       String accessToken=githubProvider.getAccessToken(accessTokenDTO);

       //获取user
       GithubUser user=githubProvider.getUser(accessToken);
       System.err.println("username:"+user.getLogin());

       if(user!=null){
           request.getSession().setAttribute("user",user);
           return "redirect:/";
       }else{
           return "redirect:/";
       }

   }

}
