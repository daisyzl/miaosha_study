package com.imooc.miaosha_study.service;

import com.imooc.miaosha_study.dao.MiaoshaUserDao;
import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.exception.GlobalException;
import com.imooc.miaosha_study.redis.MiaoshaUserKey;
import com.imooc.miaosha_study.redis.RedisService;
import com.imooc.miaosha_study.result.CodeMsg;
import com.imooc.miaosha_study.util.MD5Util;
import com.imooc.miaosha_study.util.UUIDUtil;
import com.imooc.miaosha_study.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKI_NAME_TOKEN="token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;


    public MiaoshaUser getById(Long id){
        //取缓存
       MiaoshaUser user= redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
        if(user !=null){
            return user;
        }
       user= miaoshaUserDao.getById(id);
        if(user !=null){
            redisService.set(MiaoshaUserKey.getById,""+id,user);
        }

        return user;

    }
    //对象级缓存
    public boolean updatePassword(String token, long id,String formPass){
        MiaoshaUser user=getById(id);
        if(user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        MiaoshaUser toBeUpdate=new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token,token,user);
        return true;

    }

    public Boolean login(HttpServletResponse response,LoginVo loginVo) {
        if(loginVo==null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile=loginVo.getMobile();
        String formPass=loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user=getById(Long.parseLong(mobile));
        if(user==null){
//            return CodeMsg.MOBILE_NOT_EXIST;
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);

        }
        //验证密码
        String dbPass=user.getPassword();
        String saltDB=user.getSalt();
        String calcPass=MD5Util.formPassToDBPass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
//            return CodeMsg.PASSWORD_ERROR;
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token= UUIDUtil.uuid();
        addCookie(response,token,user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie=new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user= redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        //延长有效期
        if(user!=null){
            addCookie(response,token,user);
        }
        return user;
    }
}
