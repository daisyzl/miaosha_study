package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.redis.RedisService;
import com.imooc.miaosha_study.result.CodeMsg;
import com.imooc.miaosha_study.result.Result;
import com.imooc.miaosha_study.service.MiaoshaUserService;
import com.imooc.miaosha_study.service.UserService;
import com.imooc.miaosha_study.util.ValidatorUtil;
import com.imooc.miaosha_study.vo.LoginVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    //return 相应的模板

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        System.out.println(loginVo.toString());
        //参数校验
//        String passInput=loginVo.getPassword();
//        String mobile=loginVo.getMobile();
//        if(StringUtils.isEmpty(passInput)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//
//        if(StringUtils.isEmpty(passInput)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//
//        if(!ValidatorUtil.isMobile(mobile)){
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        //登录

//        CodeMsg cm=userService.login(loginVo);
//        if(cm.getCode()==0){
//            return Result.success(true);
//        }else{
//            return Result.error(cm);
//        }
        userService.login(response,loginVo);
        return Result.success(true);

    }

}
