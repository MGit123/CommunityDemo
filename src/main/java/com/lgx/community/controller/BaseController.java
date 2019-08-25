package com.lgx.community.controller;

import com.lgx.community.dto.AccessTokenDTO;
import com.lgx.community.dto.GithubUser;
import com.lgx.community.entity.Question;
import com.lgx.community.entity.User;
import com.lgx.community.provider.GithubProvider;
import com.lgx.community.service.QuestionService;
import com.lgx.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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

    @Autowired
    private QuestionService questionService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecrct;

    @Value("${github.redirect.url}")
    private String redirectUrl;

   @GetMapping("/")
    public String index(HttpServletRequest request, Model model){

       Cookie[] cookies=request.getCookies();
       if(cookies!=null){
       for(Cookie cookie:cookies) {
           if (cookie.getName().equals("token")) {
               String token = cookie.getValue();
               User user = userService.findByToken(token);
               if (user != null) {
                   System.err.println("user:" + user);
                   request.getSession().setAttribute("user", user);
               }
               break;
           }
         }
       }

       List<Question> questionList=questionService.list();
       model.addAttribute("questionList",questionList);

       return "index";
   }

   @RequestMapping("/index")
   public String index(){
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

       if(githubUser!=null&&githubUser.getId()!=null){
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

    @RequestMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/createPublish")
    @ResponseBody
    public void createPublish(Question ques, HttpServletRequest request,HttpServletResponse response ) throws IOException {

       System.err.println("title:"+ques.getTitle()+"description:"+ques.getDescription()+"tag:"+ques.getTag());

        if(ques.getTitle()==null||ques.getTitle()==""){
            request.getSession().setAttribute("error","标题不能为空!");
             response.sendRedirect("publish");
             return;
        }

        if(ques.getDescription()==null||ques.getDescription()==""){
            request.getSession().setAttribute("error","问题内容不能为空!");
            response.sendRedirect("publish");
            return ;
        }

        if(ques.getTag()==null||ques.getTag()==""){
            request.getSession().setAttribute("error","标签不能为空!");
            response.sendRedirect("publish");
            return ;
        }

        request.getSession().setAttribute("title",ques.getTitle());
        request.getSession().setAttribute("description",ques.getDescription());
        request.getSession().setAttribute("tag",ques.getTag());


        User user=null;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
        for(Cookie cookie:cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userService.findByToken(token);
                if (user != null) {
                    System.err.println("user:" + user);
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
          }
        }

        if(user==null){
            request.getSession().setAttribute("error","用户未登录!");
            response.sendRedirect("publish");
            return ;
        }

        Question question=new Question();
        question.setTitle(ques.getTitle());
        question.setDescription(ques.getDescription());
        question.setTag(ques.getTag());
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionService.Create(question);
        request.getSession().removeAttribute("error");
        response.sendRedirect("index");
        return ;
    }

    @PostMapping("/publish")
    public String publishInfo(){
       return "/";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){

        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies) {
                if (cookie.getName().equals("token")) {
                   cookie.setMaxAge(0);
                    break;
                }
            }
        }
       return "redirect:/";

    }

}
