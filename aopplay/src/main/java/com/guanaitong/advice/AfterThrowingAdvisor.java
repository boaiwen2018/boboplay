package com.guanaitong.advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AfterThrowingAdvisor extends Advisor{

    protected Integer order = 3;

    public AfterThrowingAdvisor(Object aopObj, Method aopMethod) {
        super(aopObj, aopMethod);
    }

    public Object invoke(Exception e) throws Throwable {
        if(e instanceof InvocationTargetException){
            Throwable t = ((InvocationTargetException) e).getTargetException();
            return aopMethod.invoke(aopObj,t);
        }
        return aopMethod.invoke(aopObj,e);
    }

}
