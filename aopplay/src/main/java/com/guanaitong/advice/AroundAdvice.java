package com.guanaitong.advice;

import java.lang.reflect.Method;


public class AroundAdvice extends Advice {

    public AroundAdvice(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

    public Object invoke(Object orginalObj, Method orginalMethod) throws Throwable {
        return aopMethod.invoke(aopObj,orginalObj,orginalMethod);
    }

}
