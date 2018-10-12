package com.guanaitong.aspect;

import com.guanaitong.anno.*;
import java.lang.reflect.Method;


@MyAspectAnno
public class LogAspect {

    /**
     * MyLog注解作为切点
     */
    @MyPointcutAnno("@annotation(com.guanaitong.anno.MyLog)")
    public void pointcut() {
    }

    /**
     * 前置通知 目标方法前执行
     */
    @MyBefore("pointcut()")
    public void before() {
        System.out.println("My AOP Before Advice");
    }

    /**
     * 后置通知 目标方法后执行(无论异常是否发生)
     */
    @MyAfter("pointcut()")
    public void after() {
        System.out.println("My AOP After Advice");
    }


    /**
     * 返回通知
     * @param result
     */
    @MyAfterReturn("pointcut()")
    public void afterReturn(Object result) {
        //环绕通知处理方法
        System.out.println("My AOP AfterReturn Advice,result: "+result);
    }


    /**
     * 异常通知
     * @param e
     * @throws Throwable
     */
    @MyAfterThrowing("pointcut()")
    public void afterThrowing(Throwable e) throws Throwable {
        //环绕通知处理方法
        System.out.println("My AOP afterThrowing Advice,Exception Message: "+e.getMessage());
    }


    /**
     * 环绕通知
     * @param orginalObj
     * @param orginalMethod
     * @return
     * @throws Throwable
     */
//    @MyAround("pointcut()")
    public Object around(Object orginalObj, Method orginalMethod) throws Throwable {
        //环绕通知处理方法
        System.out.println("My AOP Around Start");
        Object obj =  orginalMethod.invoke(orginalObj);
        System.out.println("My AOP Around End");
        return obj;
    }



}
