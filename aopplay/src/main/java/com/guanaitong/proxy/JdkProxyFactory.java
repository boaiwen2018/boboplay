package com.guanaitong.proxy;


import com.guanaitong.advice.Advisor;

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

        List<Advisor> advisors = null;
        //判断方法是否需要增强
        if(methodAdvisors.containsKey(method.getName())){
            advisors = methodAdvisors.get(method.getName());
        }
        Object result = null;
        //前置通知
        advisors.get(0).invoke();
        try {
            result = method.invoke(targetObject, args);
            //后置通知
            advisors.get(1).invoke();
            //后置通知
        } catch (Exception e) {
            //异常通知  advisors.get(0).invoke();
        } finally {
            //finallyAdive()----后置通知  advisors.get(0).invoke();
        }
        return result;
    }

}
