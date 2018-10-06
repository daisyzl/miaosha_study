package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.domain.User;
import com.imooc.miaosha_study.redis.RedisService;
import com.imooc.miaosha_study.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log= LoggerFactory.getLogger(GoodsController.class);


    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_list")
    public String list(HttpServletResponse response,Model model,
//                          @CookieValue(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false)  String cookieToken,
//                          @RequestParam(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String paramToken,
                       MiaoshaUser user){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//
//        }
//        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user=userService.getByToken(response,token);
        model.addAttribute("user",user);
        return "goods_list";

    }

    @RequestMapping("/to_detail")
    public String detail(HttpServletResponse response,Model model,
//                       @CookieValue(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false)  String cookieToken,
//                       @RequestParam(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String paramToken,
                         MiaoshaUser user){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//
//        }
//        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user=userService.getByToken(response,token);
        model.addAttribute("user",user);
        return "goods_list";

    }

}
