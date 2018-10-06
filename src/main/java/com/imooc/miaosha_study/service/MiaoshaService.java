package com.imooc.miaosha_study.service;

import com.imooc.miaosha_study.dao.GoodsDao;
import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.domain.OrderInfo;
import com.imooc.miaosha_study.redis.RedisService;
import com.imooc.miaosha_study.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);

        }
    }


