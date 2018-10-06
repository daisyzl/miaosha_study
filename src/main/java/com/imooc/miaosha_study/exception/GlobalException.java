package com.imooc.miaosha_study.exception;

import com.imooc.miaosha_study.result.CodeMsg;

public class GlobalException extends RuntimeException{


    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
