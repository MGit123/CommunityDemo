package com.lgx.community.service;

import com.lgx.community.dto.PaginationDTO;
import com.lgx.community.dto.QuestionDTO;
import com.lgx.community.entity.Question;
import com.lgx.community.entity.QuestionExample;
import com.lgx.community.entity.User;
import com.lgx.community.exception.CustomizeErrorCode;
import com.lgx.community.exception.CustomizeException;
import com.lgx.community.mapper.QuestionExtMapper;
import com.lgx.community.mapper.QuestionMapper;
import com.lgx.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2019/8/25 11:03
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    public void Create(Question question){
        questionMapper.insert(question);
    }

    public PaginationDTO list(Integer page,Integer size) {

        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());

        Integer totalPage;

        if(totalCount%size==0){     //计算总共的页数
            totalPage=totalCount/size;
        }else{
            totalPage=(totalCount/size)+1;
        }

        if(page<1){    //越界处理
            page=1;
        }

        if(page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagination(page,totalPage);
        Integer offset=size*(page-1);  //起始页
        //List<Question> questions=questionMapper.list(offset,size);
        QuestionExample questionExample =new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
            //User user=userMapper.findByID(question.getCreator());
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setData(questionDTOList);
        System.err.println("评论信息收集成功!!!");
        return paginationDTO;
    }

    public PaginationDTO personList(Integer userId,Integer page,Integer size){
        PaginationDTO paginationDTO=new PaginationDTO();

        Integer totalPage;

        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount=(int)questionMapper.countByExample(questionExample);


        if(totalCount%size==0){     //计算总共的页数
            totalPage=totalCount/size;
        }else{
            totalPage=(totalCount/size)+1;
        }

        if(page<1){    //越界处理
            page=1;
        }

        if(page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagination(page,totalPage);
        Integer offset=size*(page-1);  //起始页
        //List<Question> questions=questionMapper.list(offset,size);
        //QuestionExample example=new QuestionExample();
       //questionExample.createCriteria().andCreatorEqualTo(userId);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
            //User user=userMapper.findByID(question.getCreator());
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setData(questionDTOList);
        System.err.println("个人评论信息收集成功!!!");
        return paginationDTO;

    }

    public QuestionDTO getById(Integer id){
        Question question=questionMapper.selectByPrimaryKey(id);

        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        //User user=userMapper.findByID(question.getCreator());
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question){

        if(question.getId()==null){
            //建立
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            //要修改的question部分
            Question updateQuestion=new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            //获取被修改的question
            QuestionExample example=new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());

            questionMapper.updateByExample(updateQuestion,example);
        }
    }

    public void addView(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.addView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags=StringUtils.split(questionDTO.getTag(),",");
        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question=new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions=questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS=questions.stream().map(q->{
            QuestionDTO questionDTO1=new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
                }).collect(Collectors.toList());

        return questionDTOS;
    }
}
