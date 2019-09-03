package com.lgx.community.mapper;

import com.lgx.community.entity.Question;

public interface QuestionExtMapper {

    int addView(Question record);

    int addComment(Question record);

    Question questionRelated(Question record);
}
