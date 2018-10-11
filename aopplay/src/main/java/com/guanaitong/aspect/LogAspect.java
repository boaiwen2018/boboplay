package com.guanaitong.aspect;

import com.guanaitong.anno.MyAfter;
import com.guanaitong.anno.MyAspectAnno;
import com.guanaitong.anno.MyBefore;
import com.guanaitong.anno.MyPointcutAnno;


@MyAspectAnno
public class LogAspect {

    /**
     * MyLog注解作为切点
     */
    @MyPointcutAnno("@annotation(com.guanaitong.anno.MyLog)")
    public void pointcut(){}

    @MyBefore("pointcut()")
    public void before(){
        System.out.println("My AOP Before Advice");
    }

    @MyAfter("pointcut()")
    public void after(){
        System.out.println("My AOP Before Advice");
    }

}
