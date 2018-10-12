package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AroundAdvisor extends Advisor {

    public AroundAdvisor(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

    public Object invoke(Object orginalObj, Method orginalMethod) throws Throwable {
        return aopMethod.invoke(aopObj,orginalObj,orginalMethod);
    }

}
