package com.guanaitong.advice;

import java.lang.reflect.Method;

public class AdvisorExecute {

    public void before(Advisors advisors) throws Throwable {
        if(advisors!=null&&advisors.getBeforeAdvisor()!=null){
            advisors.getBeforeAdvisor().invoke();
        }
    }

    public void after(Advisors advisors) throws Throwable {
        if(advisors!=null&&advisors.getAfterAdvisor()!=null){
            advisors.getAfterAdvisor().invoke();
        }
    }

    public void afterReturn(Advisors advisors,Object result) throws Throwable {
        if(advisors!=null&&advisors.getAfterReturnAdvisor()!=null){
            advisors.getAfterReturnAdvisor().invoke(result);
        }
    }

    public void afterThrowing(Advisors advisors, Exception e) throws Throwable {
        if(advisors!=null&&advisors.getAfterThrowingAdvisor()!=null){
            advisors.getAfterThrowingAdvisor().invoke(e);
        }
    }

    public boolean around(Advisors advisors, Object targetObject, Method method) throws Throwable {
        boolean flag = false;
        if(advisors!=null&&advisors.getAroundAdvisor()!=null){
            advisors.getAroundAdvisor().invoke(targetObject,method);
            flag = true;
        }
        return flag;
    }
}
