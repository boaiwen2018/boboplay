package com.guanaitong.proxy;


import com.guanaitong.advice.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * JDK代理工厂类
 */
public class JdkProxyFactory extends AdvisorExecute implements InvocationHandler {

    private Object targetObject;
    private Map<String, Advisors> methodAdvisors;

    public JdkProxyFactory() {
    }

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
        Advisors advisors = getAdvisors(methodAdvisors, method);
        Object result = null;
        //before前置通知
        before(advisors);
        try {
            //around环绕通知
            if (!around(advisors, targetObject, method)) {
                result = method.invoke(targetObject, args);
            }
            //afterReturn返回通知
            afterReturn(advisors, result);
        } catch (Exception e) {
            //afterThrowing异常通知
            afterThrowing(advisors, e);
        } finally {
            //after后置通知
            after(advisors);
        }
        return result;
    }

    private Advisors getAdvisors(Map<String, Advisors> methodAdvisors, Method method) {
        //判断方法是否需要增强
        if (methodAdvisors.containsKey(method.getName())) {
            return methodAdvisors.get(method.getName());
        }
        return null;
    }


}
