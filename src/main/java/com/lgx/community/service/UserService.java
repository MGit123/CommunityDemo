package com.lgx.community.service;

import com.lgx.community.entity.User;
import com.lgx.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2019/8/24 21:24
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }

    public void createOrUpdate(User user) {

       User dbUser=userMapper.findByAccountId(user.getAccountId());
       if(dbUser==null){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else{
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setGmtModified(System.currentTimeMillis());
           userMapper.update(dbUser);
       }

    }
}
