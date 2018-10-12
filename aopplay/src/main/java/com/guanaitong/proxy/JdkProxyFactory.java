package com.guanaitong.proxy;


import com.guanaitong.advice.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * JDK代理工厂类
 */
public class JdkProxyFactory implements InvocationHandler {

    private Object targetObject;
    private Map<String, Advisors> methodAdvisors;

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public JdkProxyFactory() {}

    public Object createProxyInstance(Object targetObject, Map<String, Advisors> methodAdvisors) {
        this.targetObject = targetObject;
        this.methodAdvisors = methodAdvisors;
        return Proxy.newProxyInstance(
                //类加载器
                targetObject.getClass().getClassLoader(),
                //获取目标对象接口
                targetObject.getClass().getInterfaces(),
                //InvocationHandler对象
                this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Advisors advisors = getAdvisors(methodAdvisors,method);
        Object result = null;

        //环绕通知
        if(!around(advisors,targetObject,method)){
            //before前置通知
            before(advisors);
            try {
                result = method.invoke(targetObject, args);
                //返回通知
                afterReturn(advisors,result);
            } catch (Exception e) {
                //异常通知
                afterThrowing(advisors,e);
            } finally {
                after(advisors);
            }
        }
        return result;
    }

    private Advisors getAdvisors(Map<String, Advisors> methodAdvisors,Method method) {
        //判断方法是否需要增强
        if(methodAdvisors.containsKey(method.getName())){
            return methodAdvisors.get(method.getName());
        }
        return null;
    }


    void before(Advisors advisors) throws Throwable {
        if(advisors!=null&&advisors.getBeforeAdvisor()!=null){
            advisors.getBeforeAdvisor().invoke();
        }
    }

    void after(Advisors advisors) throws Throwable {
        if(advisors!=null&&advisors.getAfterAdvisor()!=null){
            advisors.getAfterAdvisor().invoke();
        }
    }

    void afterReturn(Advisors advisors,Object result) throws Throwable {
        if(advisors!=null&&advisors.getAfterReturnAdvisor()!=null){
            advisors.getAfterReturnAdvisor().invoke(result);
        }
    }

    void afterThrowing(Advisors advisors, Exception e) throws Throwable {
        if(advisors!=null&&advisors.getAfterThrowingAdvisor()!=null){
            advisors.getAfterThrowingAdvisor().invoke(e);
        }
    }

    boolean around(Advisors advisors, Object targetObject, Method method) throws Throwable {
        boolean flag = false;
        if(advisors!=null&&advisors.getAroundAdvisor()!=null){
            advisors.getAroundAdvisor().invoke(targetObject,method);
            flag = true;
        }
        return flag;
    }

}
