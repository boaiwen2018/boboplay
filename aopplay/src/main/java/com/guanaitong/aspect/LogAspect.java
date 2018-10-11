package com.guanaitong.aspect;

import com.guanaitong.anno.*;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;


@MyAspectAnno
public class LogAspect {

    /**
     * MyLog注解作为切点
     */
    @MyPointcutAnno("@annotation(com.guanaitong.anno.MyLog)")
    public void pointcut() {
    }

    @MyBefore("pointcut()")
    public void before() {
        System.out.println("My AOP Before Advice");
    }

    @MyAfter("pointcut()")
    public void after() {
        System.out.println("My AOP Before Advice");
    }

    @MyAround("pointcut()")
    public Object around(Object orginalObj, Method orginalMethod) throws Throwable {
        //环绕通知处理方法
        System.out.println("My AOP Around Start");
        Object obj =  orginalMethod.invoke(orginalObj);
        System.out.println("My AOP Around End");
        return obj;
    }


}
