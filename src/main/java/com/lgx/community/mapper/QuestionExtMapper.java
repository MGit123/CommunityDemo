package com.lgx.community.mapper;

import com.lgx.community.entity.Question;

import java.util.List;

public interface QuestionExtMapper {

    int addView(Question record);

    int addComment(Question record);

    List<Question> selectRelated(Question record);
}
