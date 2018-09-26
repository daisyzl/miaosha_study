package com.imooc.miaosha_study.service;

import com.imooc.miaosha_study.dao.UserDao;
import com.imooc.miaosha_study.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);

    }

}
