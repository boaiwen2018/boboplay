package com.guanaitong.advice;

import java.lang.reflect.InvocationHandler;

public interface MyAdvice extends InvocationHandler {

    void before();

    void after();

//    void afterReturn();
//
//    void afterThrowing();
}
