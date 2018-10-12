package com.guanaitong;

import com.guanaitong.service.PlayService;
import com.guanaitong.service.SleepService;
import com.guanaitong.util.ContextBean;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Map<String,Object> beanMap = ContextBean.getInstance().initBean();

        //从容器中获取bean
        PlayService playService = (PlayService) beanMap.get("playService");
        SleepService sleepService = (SleepService) beanMap.get("sleepService");

        playService.play();
        System.out.println("-----------华丽的分割线-------------");
        playService.play2();
        System.out.println("-----------华丽的分割线-------------");
        sleepService.sleep();
    }
}
