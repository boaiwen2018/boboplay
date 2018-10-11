package com.guanaitong.proxy;


import com.guanaitong.advice.Advisor;
import com.guanaitong.advice.AroundAdvisor;

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
    private Map<String, List<Advisor>> methodAdvisors;

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public JdkProxyFactory() {}

    public Object createProxyInstance(Object targetObject, Map<String, List<Advisor>> methodAdvisors) {
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

        List<Advisor> advisors = getAdvisors(methodAdvisors,method);
        Object result = null;
        //before前置通知
        before(advisors);
        try {
            //环绕通知
            if(!around(advisors,targetObject,method)){
                result = method.invoke(targetObject, args);
            }
            //after后置通知
            after(advisors);
        } catch (Exception e) {
            //异常通知
            afterThrowing(advisors);
        } finally {
            afterReturn(advisors);
        }
        return result;
    }

    private List<Advisor> getAdvisors(Map<String, List<Advisor>> methodAdvisors,Method method) {
        List<Advisor> advisors = null;
        //判断方法是否需要增强
        if(methodAdvisors.containsKey(method.getName())){
            advisors = methodAdvisors.get(method.getName());
        }
        return advisors;
    }


    void before(List<Advisor> advisors) throws Throwable {
        if(advisors!=null&&advisors.size()>0){
            advisors.get(0).invoke();
        }
    }

    void after(List<Advisor> advisors) throws Throwable {
        if(advisors!=null&&advisors.size()>1){
            advisors.get(1).invoke();
        }
    }

    void afterReturn(List<Advisor> advisors) throws Throwable {
        if(advisors!=null&&advisors.size()>4){
            advisors.get(4).invoke();
        }
    }

    void afterThrowing(List<Advisor> advisors) throws Throwable {
        if(advisors!=null&&advisors.size()>3){
            advisors.get(3).invoke();
        }
    }

    boolean around(List<Advisor> advisors, Object targetObject, Method method) throws Throwable {
        boolean flag = false;
        if(advisors!=null&&advisors.size()>2){
            AroundAdvisor aroundAdvisor = (AroundAdvisor) advisors.get(2);
            aroundAdvisor.invoke(targetObject,method);
            flag = true;
        }
        return flag;
    }

}
