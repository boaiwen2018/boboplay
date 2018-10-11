package com.guanaitong.advice;

import java.lang.reflect.Method;

public abstract class Advisor {

    protected Object aopObj;
    protected Method aopMethod;

    public Advisor(Object aopObj, Method aopMethod){
        this.aopObj = aopObj;
        this.aopMethod = aopMethod;
    }

    public Object invoke() throws Throwable {
        return aopMethod.invoke(aopObj);
    }

}
