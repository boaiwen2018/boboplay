package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AfterAdvisor extends Advisor {

    public AfterAdvisor(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }
}
