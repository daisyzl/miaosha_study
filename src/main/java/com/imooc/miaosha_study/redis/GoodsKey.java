package com.imooc.miaosha_study.redis;

public class GoodsKey extends BasePrefix{

    private GoodsKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList=new GoodsKey(60,"gl");

}
