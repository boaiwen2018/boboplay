package com.guanaitong.proxy;


import com.guanaitong.aspect.LogAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理工厂类
 */
public class AopProxyFactory implements InvocationHandler {

    private Object targetObject;
    private LogAspect logAspect;

    public AopProxyFactory() {}

    public Object createProxyInstance(Object targetObject,LogAspect logAspect) {
        this.targetObject = targetObject;
        this.logAspect = logAspect;
        return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(),
                this.targetObject.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //--环绕通知
//        StudentService bean = (StudentService) this.targetObject;
        Object result = null;
        //前置通知
        logAspect.before();
        try {
            result = method.invoke(targetObject, args);
            logAspect.after();
            //后置通知
        } catch (Exception e) {
            //异常通知
        } finally {
            //finallyAdive()----后置通知
        }
        return result;
    }

}
