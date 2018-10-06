package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.domain.MiaoshaOrder;
import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.domain.OrderInfo;
import com.imooc.miaosha_study.result.CodeMsg;
import com.imooc.miaosha_study.service.GoodsService;
import com.imooc.miaosha_study.service.MiaoshaService;
import com.imooc.miaosha_study.service.OrderService;
import com.imooc.miaosha_study.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String  list(Model model, MiaoshaUser user,
                                   @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user==null){
        return "login";
        }
        System.out.println("22222");
        System.out.println(goodsId);
        //判断库存
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        int stock=goods.getStockCount();
        System.out.println(goods.toString());
        System.out.println(stock);
        if(stock< 0){
            model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER.getMsg());
            System.out.println("dsdsds");
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order=orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order!=null){
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            System.out.println("111111111");
            return "miaosha_fail";

        }
        //减库存，下订单，写入秒杀订单，事务
        OrderInfo orderInfo=miaoshaService.miaosha(user,goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        System.out.println("fffffff");
        return "order_detail";
    }


}
