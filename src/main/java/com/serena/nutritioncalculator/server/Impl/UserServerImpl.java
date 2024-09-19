package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.*;
import com.serena.nutritioncalculator.model.User;
import com.serena.nutritioncalculator.server.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServerImpl implements UserServer {
    @Autowired
    UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(UserServerImpl.class);
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user!=null){
            log.warn("email: {}已被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        if(user==null){
            log.warn("email: {}尚未註冊",email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if ( user == null ){
            log.warn("帳號{} 尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 將密碼轉為MD5
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        userLoginRequest.setPassword(hashedPassword);
        if (user.getPassword().equals(userLoginRequest.getPassword()) && user.getUserName().equals(userLoginRequest.getUserName())){
            return user;
        }else {
            log.warn("輸入錯誤");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
