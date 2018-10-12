package com.guanaitong;

import com.guanaitong.service.PlayService;
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
        Map<String,Object> beanMap = new ContextBean().initBean();
        PlayService playService = (PlayService) beanMap.get("playService");
        playService.play();
    }
}
