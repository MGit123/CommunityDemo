package com.lgx.community.service;

import com.lgx.community.dto.NotificationDTO;
import com.lgx.community.dto.PaginationDTO;
import com.lgx.community.dto.QuestionDTO;
import com.lgx.community.entity.*;
import com.lgx.community.exception.NotificationStatusEnum;
import com.lgx.community.exception.NotificationTypeEnum;
import com.lgx.community.mapper.NotificationMapper;
import com.lgx.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2019/9/5 17:04
 */

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO=new PaginationDTO();

        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount=(int)notificationMapper.countByExample(notificationExample);

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

        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds(notificationExample,
                new RowBounds(offset,size));

        if(notifications.size()==0){
            return paginationDTO;
        }

       List<NotificationDTO> notificationDTOList=new ArrayList<>();
        for(Notification notification:notifications){
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.typeOfName(notification.getType()));
            notificationDTOList.add(notificationDTO);

        }

        paginationDTO.setData(notificationDTOList);
        System.err.println("通知信息收集成功!!!");
        return paginationDTO;
    }

    public int unReadCount(Integer userId) {
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.NOTREAD.getStatus());
        return (int)notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification=notificationMapper.selectByPrimaryKey(id);

        NotificationDTO notificationDTO=new NotificationDTO();
        return notificationDTO;
    }
}
