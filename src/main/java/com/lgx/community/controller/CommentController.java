package com.lgx.community.controller;

import com.lgx.community.dto.CommentDTO;
import com.lgx.community.dto.CreateCommentDTO;
import com.lgx.community.dto.ResultDTO;
import com.lgx.community.entity.Comment;
import com.lgx.community.entity.User;
import com.lgx.community.exception.CommentTypeEnum;
import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author admin
 * @date 2019/8/30 21:00
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value="/comment")
    @ResponseBody
    public Object Post(@RequestBody CreateCommentDTO commentDTO,
                       HttpServletRequest request){

        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
       Comment comment=new Comment();
       comment.setParentId(commentDTO.getParentId());
       comment.setContent(commentDTO.getContent());
       comment.setType(commentDTO.getType());
       comment.setGmtCreate(System.currentTimeMillis());
       comment.setGmtModified(comment.getGmtCreate());
       comment.setCommentor(1);
       comment.setLikeCount(0);
       comment.setCommentCount(0);
       commentService.insert(comment);
       return ResultDTO.okOf();
    }

    @RequestMapping(value="/comment/{id}")
    @ResponseBody
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name="id") Integer id){
        List<CommentDTO> commentDTOList=commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        System.err.println("我来到这里啦");
        return ResultDTO.okOf(commentDTOList);

    }

}
