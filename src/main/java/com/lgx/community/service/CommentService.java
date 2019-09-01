package com.lgx.community.service;

import com.lgx.community.dto.CommentDTO;
import com.lgx.community.entity.*;
import com.lgx.community.exception.CommentTypeEnum;
import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.exception.CustomizeException;
import com.lgx.community.mapper.CommentMapper;
import com.lgx.community.mapper.QuestionExtMapper;
import com.lgx.community.mapper.QuestionMapper;
import com.lgx.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2019/8/31 10:24
 */

@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;


    //事物注解，当多个数据操作有一个出问题时，就发生回滚
    @Transactional
    public void insert(Comment comment) {

        if(comment.getParentId()==null||comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGT_PARAM_NOT_FOUND);
        }

        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment=commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }else{
            //回复问题
            Question question=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.addComment(question);
        }
    }

    public List<CommentDTO> listByQuestionId(Integer id) {

        //获取评论类型为1的评论
        CommentExample commentExample=new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).
                andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments=commentMapper.selectByExample(commentExample);

        System.err.println("评论个数为:"+comments.size());

        if(comments.size()==0){
            return  new ArrayList<>();
        }

        //获取去重的评论人Id
        Set<Integer> commentors= comments.stream().map(comment->comment.getCommentor()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(commentors);

        //获取评论人并转换为Map
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users=userMapper.selectByExample(userExample);
        Map<Integer,User> userMap=users.stream().collect(Collectors.toMap(user->user.getId(), user->user));

        List<CommentDTO> commentDTOS=comments.stream().map(
         comment -> {CommentDTO commentDTO=new CommentDTO();
             BeanUtils.copyProperties(comment,commentDTO);
             commentDTO.setUser(userMap.get(comment.getCommentor()));
         return commentDTO;
         }).collect(Collectors.toList());

        System.err.println("CommentDTO个数为:"+commentDTOS.size());

       return commentDTOS;

    }
}
