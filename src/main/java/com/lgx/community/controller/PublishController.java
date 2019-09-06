package com.lgx.community.controller;

import com.lgx.community.dto.QuestionDTO;
import com.lgx.community.dto.TagDTO;
import com.lgx.community.entity.Question;
import com.lgx.community.entity.User;
import com.lgx.community.provider.TagCache;
import com.lgx.community.service.QuestionService;
import com.lgx.community.service.UserService;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
    public String publish(Model model) {
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Integer id, Model model){

        QuestionDTO question=questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

   /* @PostMapping("/publish")
    public String publishInfo(){
        return "/";
    } */

    @RequestMapping("/createPublish")
    public String createPublish(@RequestParam(value="title", required = false) String title,
                              @RequestParam(value="description", required = false) String description,
                              @RequestParam(value="tag", required = false) String tag,
                              @RequestParam(value="id", required = false) Integer id,
                              HttpServletRequest request, HttpServletResponse response, Model model ) throws IOException {

        System.err.println("title:"+title+"description:"+description+"tag:"+tag);
        model.addAttribute("tags",TagCache.get());

        if(title==null||title==""){
            model.addAttribute("error","标题不能为空!");
            System.err.println(1);
            return "publish";
        }

        if(description==null||description==""){
            model.addAttribute("error","问题内容不能为空!");
            System.err.println(2);
            return "publish";
        }

        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空!");
            System.err.println(3);
            return "publish";
        }

        String IsValid=TagCache.filterIsValid(tag);
        if(StringUtils.isNotBlank(IsValid)){
            model.addAttribute("error","输入非法标签:"+IsValid);
            System.err.println(4);
            return "publish";
        }

        User user=(User)request.getSession().getAttribute("user");

        if(user==null){
            model.addAttribute("error","用户未登录!");
            System.err.println(5);
            return "publish";
        }

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        Question question=new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setCommentCount(0);
        question.setViewCount(0);
        question.setGmtCreate(System.currentTimeMillis());
        questionService.createOrUpdate(question);
        return "redirect:/";

    }
}
