package com.imooc.miaosha_study.redis;

public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();

}
