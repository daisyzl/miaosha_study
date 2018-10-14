package com.imooc.miaosha_study.dao;

import com.imooc.miaosha_study.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaUserDao {
    @Select("select * from miaosha_user where id=#{id}")
    public MiaoshaUser getById(@Param("id") long id);

    @Update("update miaosha_user set password=#{password} where id=#{id}")
    //不更新的东西不要进行传递
    public void update(MiaoshaUser toBeUpdate);
}
