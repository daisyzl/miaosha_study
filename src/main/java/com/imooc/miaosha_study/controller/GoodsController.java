package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.domain.User;
import com.imooc.miaosha_study.redis.RedisService;
import com.imooc.miaosha_study.service.GoodsService;
import com.imooc.miaosha_study.service.MiaoshaUserService;
import com.imooc.miaosha_study.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log= LoggerFactory.getLogger(GoodsController.class);


    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

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
        //查询商品列表
        List<GoodsVo> goods=goodsService.listGoodsVo();
        model.addAttribute("goodsList",goods);
        return "goods_list";

    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(HttpServletResponse response, Model model,
//                       @CookieValue(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false)  String cookieToken,
//                       @RequestParam(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String paramToken,
                         MiaoshaUser user, @PathVariable("goodsId") long goodsId){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//
//        }
//        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user=userService.getByToken(response,token);
        model.addAttribute("user",user);
        //snowflake 分布式id生成算法
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();
        int miaoshaStatus=0;
        int remainSeconds=0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";

    }

}
