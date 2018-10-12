package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AfterReturnAdvice extends Advice{

    public AfterReturnAdvice(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

    public Object invoke(Object result) throws Throwable {
        return aopMethod.invoke(aopObj,result);
    }

}
