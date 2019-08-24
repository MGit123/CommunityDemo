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

    public void insert (User user){
        userMapper.insert(user);
    }

    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }

}
