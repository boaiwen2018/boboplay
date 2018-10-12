package com.guanaitong.advice;

import java.lang.reflect.Method;

public class AdviceExecute {

    public void before(Advices advices) throws Throwable {
        if(advices!=null&&advices.getBeforeAdvice()!=null){
            advices.getBeforeAdvice().invoke();
        }
    }

    public void after(Advices advices) throws Throwable {
        if(advices!=null&&advices.getAfterAdvice()!=null){
            advices.getAfterAdvice().invoke();
        }
    }

    public void afterReturn(Advices advices,Object result) throws Throwable {
        if(advices!=null&&advices.getAfterReturnAdvice()!=null){
            advices.getAfterReturnAdvice().invoke(result);
        }
    }

    public void afterThrowing(Advices advices, Exception e) throws Throwable {
        if(advices!=null&&advices.getAfterThrowingAdvice()!=null){
            advices.getAfterThrowingAdvice().invoke(e);
        }
    }

    public boolean around(Advices advices, Object targetObject, Method method) throws Throwable {
        boolean flag = false;
        if(advices!=null&&advices.getAroundAdvice()!=null){
            advices.getAroundAdvice().invoke(targetObject,method);
            flag = true;
        }
        return flag;
    }
}
