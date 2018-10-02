package com.imooc.miaosha_study.service;

import com.imooc.miaosha_study.dao.UserDao;
import com.imooc.miaosha_study.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);

    }

    /*
    对事务的构造
     */
    @Transactional
    public boolean tx() {
        User u1=new User();
        u1.setId(2);
        u1.setName("2222");
        userDao.insert(u1);

        User u2=new User();
        u1.setId(1);
        u1.setName("1111");
        userDao.insert(u2);
        return true;
    }
}
