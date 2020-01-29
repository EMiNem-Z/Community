package com.community.community.Service;

import com.community.community.entity.User;
import com.community.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void addUser(User user){
        userMapper.insert(user);
    }

    public User findUserByToken(String token){
        return userMapper.findUserByToken(token);
    }

    public User findUserByAccountId(String accountId){
        return userMapper.findUserByAccountId(accountId);
    }

    private void update(User dbUser) {
        userMapper.update(dbUser);
    }

    public void createOrUpdate(User user) {
        User dbUser = findUserByAccountId(user.getAccountId());
        if(dbUser == null){
            user.setGmtModify(user.getGmtCreate());
            addUser(user);
        }else{
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModify(System.currentTimeMillis());
            update(dbUser);
        }
    }


}
