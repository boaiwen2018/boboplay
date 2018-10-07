package com.guanaitong;

import com.guanaitong.anno.MyAspectAnno;
import com.guanaitong.anno.MyServiceAnno;
import com.guanaitong.aspect.LogAspect;
import com.guanaitong.proxy.AopProxyFactory;
import com.guanaitong.service.PlayService;
import com.guanaitong.service.impl.PlayServiceImpl;
import com.guanaitong.util.ContextBean;
import javafx.application.Application;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //1.初始化容器 将所有组件装入bean容器中,例如MyServiceAnno,MyAspect
        Map<String,Object> beanMap = new ContextBean().initBean();





//        AopProxyFactory factory =new AopProxyFactory();
//        PlayService playService = (PlayService) factory.createProxyInstance(new PlayServiceImpl(),new LogAspect());
//
//        playService.play();












    }
}
