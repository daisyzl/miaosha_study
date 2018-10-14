package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.domain.MiaoshaOrder;
import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.domain.OrderInfo;
import com.imooc.miaosha_study.result.CodeMsg;
import com.imooc.miaosha_study.result.Result;
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

    @PostMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model, MiaoshaUser user,
                                  @RequestParam("goodsId")long goodsId) {
        if(user==null){
        return Result.error(CodeMsg.SESSION_ERROR);
        }
        System.out.println("22222");
        System.out.println(goodsId);
        //判断库存
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        int stock=goods.getStockCount();
        System.out.println(goods.toString());
        System.out.println(stock);
        if(stock<=0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order=orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order!=null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);

        }
        //减库存，下订单，写入秒杀订单，事务
        OrderInfo orderInfo=miaoshaService.miaosha(user,goods);
        return Result.success(orderInfo);
    }


}
