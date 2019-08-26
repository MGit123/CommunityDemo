package com.lgx.community.service;

import com.lgx.community.dto.PaginationDTO;
import com.lgx.community.dto.QuestionDTO;
import com.lgx.community.entity.Question;
import com.lgx.community.entity.User;
import com.lgx.community.mapper.QuestionMapper;
import com.lgx.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2019/8/25 11:03
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void Create(Question question){
        questionMapper.Create(question);
    }

    public PaginationDTO list(Integer page,Integer size) {

        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalCount=questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        if(page<1){    //越界处理
            page=1;
        }

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        Integer offset=size*(page-1);  //起始页
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
            User user=userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);
        System.err.println("评论信息收集成功!!!");
        return paginationDTO;
    }
}
