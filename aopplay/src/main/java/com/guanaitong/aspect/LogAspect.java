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


    //1.扫包的时候扫描@MyAspectAnno注解 ，找出切点MyPointcutAnno ，用一个map存储 key为切点类型，如(annotation或excution) ，value为  如果是注解 定义一个注解对应有哪些增强Advice （考虑使用一个链表存放advice的先后顺序）

    //2.对需要增强的方法的类生成代理类  扫包时 将bean装载到context容器的时候 判断方法上是否有MyLog注解 有的话生成代理类 判断是否有MyBefore、MyAfter，如果有的话




}
