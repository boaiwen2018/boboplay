package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AfterReturnAdvisor extends Advisor{

    public AfterReturnAdvisor(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

    public Object invoke(Object result) throws Throwable {
        return aopMethod.invoke(aopObj,result);
    }

}
