package com.imooc.miaosha_study.dao;

import com.imooc.miaosha_study.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where id=#{id}")
    public User getById(@Param("id") int id);

}
