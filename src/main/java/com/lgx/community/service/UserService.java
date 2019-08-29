package com.lgx.community.service;

import com.lgx.community.entity.User;
import com.lgx.community.entity.UserExample;
import com.lgx.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @date 2019/8/24 21:24
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {

        UserExample userExample=new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
       List<User> users=userMapper.selectByExample(userExample);
      // User dbUser=userMapper.findByAccountId(user.getAccountId());
       if(users.size()==0){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else{
           //更新
           User dbUser=users.get(0);
           User updateUser=new User();
           updateUser.setName(user.getName());
           updateUser.setToken(user.getToken());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setGmtModified(System.currentTimeMillis());
           UserExample example=new UserExample();
           example.createCriteria().andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser,example);
       }

    }
}
