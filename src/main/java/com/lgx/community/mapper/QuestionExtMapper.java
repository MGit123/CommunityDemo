package com.lgx.community.mapper;

import com.lgx.community.dto.QuestionQueryDTO;
import com.lgx.community.entity.Question;

import java.util.List;

public interface QuestionExtMapper {

    int addView(Question record);

    int addComment(Question record);

    List<Question> selectRelated(Question record);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
