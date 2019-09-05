package com.lgx.community.controller;

import com.lgx.community.dto.NotificationDTO;
import com.lgx.community.dto.PaginationDTO;
import com.lgx.community.entity.User;
import com.lgx.community.service.NotificationService;
import com.lgx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author admin
 * @date 2019/8/26 20:53
 */

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/profile/{action}")
    public String ProfileAction(@PathVariable(name="action") String action, Model model,
                                @RequestParam(name="page",defaultValue ="1") Integer page,
                                @RequestParam(name="size",defaultValue ="5") Integer size,
                                HttpServletRequest request){

        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

        if("questions".equals(action)){

            PaginationDTO paginationDTO=questionService.personList(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("repies".equals(action)){
            PaginationDTO paginationDTO=notificationService.list(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }



        return "profile";
    }
}
