package com.lgx.community.controller;

import com.lgx.community.dto.ResultDTO;
import com.lgx.community.entity.User;
import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.mapper.NotificationMapper;
import com.lgx.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 * @date 2019/9/5 17:05
 */

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public String edit(@PathVariable(name="id") Integer id, Model model,
                       HttpServletRequest request) {

        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "/";
        }
       notificationService.read(id,user);
               return "profile";
    }
}
