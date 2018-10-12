package com.guanaitong.proxy;


import com.guanaitong.advice.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * JDK代理工厂类
 */
public class JdkProxyFactory extends AdviceExecute implements InvocationHandler {

    private Object targetObject;
    private Map<String, Advices> methodAdvices;

    public JdkProxyFactory() {
    }

    public Object createProxyInstance(Object targetObject, Map<String, Advices> methodAdvices) {
        this.targetObject = targetObject;
        this.methodAdvices = methodAdvices;
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
        Advices advices = getAdvices(methodAdvices, method);
        Object result = null;
        //before前置通知
        before(advices);
        try {
            //around环绕通知
            if (!around(advices, targetObject, method)) {
                result = method.invoke(targetObject, args);
            }
            //afterReturn返回通知
            afterReturn(advices, result);
        } catch (Exception e) {
            //afterThrowing异常通知
            afterThrowing(advices, e);
        } finally {
            //after后置通知
            after(advices);
        }
        return result;
    }

    private Advices getAdvices(Map<String, Advices> methodAdvices, Method method) {
        //判断方法是否需要增强
        if (methodAdvices.containsKey(method.getName())) {
            return methodAdvices.get(method.getName());
        }
        return null;
    }


}
