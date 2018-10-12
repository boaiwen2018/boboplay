package com.guanaitong.aspect;

import com.guanaitong.anno.*;

import java.lang.reflect.Method;


@MyAspectAnno
public class LogAspect2 {

    /**
     * MyLog2注解作为切点
     */
    @MyPointcutAnno("@annotation(com.guanaitong.anno.MyLog2)")
    public void pointcut() {}

    /**
     * 环绕通知
     * @param orginalObj
     * @param orginalMethod
     * @return
     * @throws Throwable
     */
    @MyAround("pointcut()")
    public Object around(Object orginalObj, Method orginalMethod) throws Throwable {
        //环绕通知处理方法
        System.out.println("My AOP Around Start");
        Object obj =  orginalMethod.invoke(orginalObj);
        System.out.println("My AOP Around End");
        return obj;
    }



}
