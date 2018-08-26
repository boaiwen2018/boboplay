package com.guanaitong.exception;

/**
 * @author Administrator
 * @date 2018/8/26 13:35
 */
public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("捕获异常,异常出现在"+t.getName()+",原因:"+e.getMessage());
    }
}
