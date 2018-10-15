package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.domain.OrderInfo;
import com.imooc.miaosha_study.redis.RedisService;
import com.imooc.miaosha_study.result.CodeMsg;
import com.imooc.miaosha_study.result.Result;
import com.imooc.miaosha_study.service.GoodsService;
import com.imooc.miaosha_study.service.MiaoshaUserService;
import com.imooc.miaosha_study.service.OrderService;
import com.imooc.miaosha_study.vo.GoodsVo;
import com.imooc.miaosha_study.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
