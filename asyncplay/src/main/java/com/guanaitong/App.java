package com.guanaitong;

import com.guanaitong.async.service.UserService;
import com.guanaitong.async.service.impl.UserServiceImpl;
import com.guanaitong.async.util.ContextUtil;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long starTime = System.currentTimeMillis();
        //初始化bean
        Map<String,Object> beanMap = ContextUtil.loadBean("applicationContext.xml");

        System.out.println("main方法开始");
        UserService userService = (UserService) beanMap.get("userService");
        userService.play();
        Future<String> result = userService.play2();
        while(result==null){
            System.out.println("未得到play2的返回结果,等下一个3秒");
            Thread.sleep(1000);
        }
        System.out.println("play2方法返回值为:"+result.get());
//        userService.play3();
        System.out.println("main方法结束");
        System.out.println("共消耗时间:"+(System.currentTimeMillis()-starTime)+"ms");
    }
}
