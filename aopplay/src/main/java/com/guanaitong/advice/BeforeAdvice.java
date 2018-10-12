package com.guanaitong.advice;

import java.lang.reflect.Method;


public class BeforeAdvice extends Advice{

    public BeforeAdvice(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

}
