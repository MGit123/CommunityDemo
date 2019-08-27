package com.lgx.community.controller;

import com.lgx.community.entity.Question;
import com.lgx.community.entity.User;
import com.lgx.community.service.QuestionService;
import com.lgx.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 * @date 2019/8/26 12:53
 */

@Controller
public class PublishController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String publishInfo(){
        return "/";
    }

    @PostMapping("/createPublish")
    @ResponseBody
    public void createPublish(Question ques, HttpServletRequest request, HttpServletResponse response, Model model ) throws IOException {

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



        User user=(User)request.getSession().getAttribute("user");

        if(user==null){
            request.getSession().setAttribute("error","用户未登录!");
            response.sendRedirect("publish");
            return ;
        }

        request.getSession().setAttribute("title",ques.getTitle());
        request.getSession().setAttribute("description",ques.getDescription());
        request.getSession().setAttribute("tag",ques.getTag());

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
}
