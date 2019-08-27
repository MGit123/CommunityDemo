package com.lgx.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author admin
 * @date 2019/8/26 20:53
 */

@Controller
public class ProfileController {

    @RequestMapping("/profile/{action}")
    public String ProfileAction(@PathVariable(name="action") String action, Model model){

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("repies".equals(action)){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
