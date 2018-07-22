package com.guanaitong.springioc.context;

public class ContextUtils {
    /**
     * 根据beanId获取bean
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId){
        return ContextMap.getInstance().get(beanId);
    }
}
