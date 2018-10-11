package com.guanaitong.advice;

import java.lang.reflect.Method;


public class BeforeAdvisor extends Advisor{

    protected Integer order = 0;

    public BeforeAdvisor(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

}
