package com.lgx.community.service;

import com.lgx.community.entity.Question;
import com.lgx.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @date 2019/8/25 11:03
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    public void Create(Question question){
        questionMapper.Create(question);
    }

    public List<Question> list() {
        return questionMapper.list();
    }
}
