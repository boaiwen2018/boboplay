package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AfterAdvice extends Advice {

    public AfterAdvice(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }
}
