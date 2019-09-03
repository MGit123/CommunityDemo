package com.lgx.community.controller;

import com.lgx.community.dto.CommentDTO;
import com.lgx.community.dto.QuestionDTO;
import com.lgx.community.exception.CommentTypeEnum;
import com.lgx.community.service.CommentService;
import com.lgx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author admin
 * @date 2019/8/28 15:52
 */

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Transactional
    @RequestMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id, Model model ){

        QuestionDTO questionDTO=questionService.getById(id);
        questionService.addView(id);
       List<CommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comments);
        return "question";

    }

    @RequestMapping("profile/question/{id}")
    public String personQuestion(@PathVariable(name="id") Integer id, Model model ){

        QuestionDTO questionDTO=questionService.getById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";

    }
}
