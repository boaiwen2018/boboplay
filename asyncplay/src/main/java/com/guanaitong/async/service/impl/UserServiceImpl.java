package com.guanaitong.async.service.impl;

import com.guanaitong.async.anno.CustomAsync;
import com.guanaitong.async.service.UserService;

import java.util.concurrent.*;

/**
 * @author Administrator
 * @date 2018/8/26 9:12
 */
//@CustomAsync
public class UserServiceImpl implements UserService{

    @Override
    @CustomAsync
    public void play() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("user play......");
    }

    @Override
    @CustomAsync
    public Future<String> play2() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("user play2......");
        return new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return "user play2 over...";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return "user play2 timeout...";
            }
        };
    }

//    @Override
//    @CustomAsync
//    public void play3(){
//        System.out.println("user play3......");
//        if(true)
//            throw new RuntimeException("我报错了~~~");
//    }

}

