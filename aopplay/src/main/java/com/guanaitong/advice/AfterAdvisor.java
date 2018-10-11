package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AfterAdvisor extends Advisor {

    protected Integer order = 10;

    public AfterAdvisor(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }
}
