package com.lgx.community.mapper;


import com.lgx.community.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void Create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value="offset") Integer offset, @Param(value="size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where id=#{id}")
    Question getById(@Param(value="id") Integer id);

    @Update("update question set title=#{title}, description=#{description}, tag=#{tag} where id=#{id}")
    void Update(Question question);
}
