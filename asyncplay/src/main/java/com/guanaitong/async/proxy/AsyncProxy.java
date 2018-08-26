package com.guanaitong.async.proxy;

import com.guanaitong.async.anno.CustomAsync;
import com.guanaitong.async.util.ContextUtil;
import com.guanaitong.exception.CustomExceptionHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author Administrator
 * @date 2018/8/26 9:03
 */
public class AsyncProxy implements InvocationHandler {

    //目标对象
    Object targetObject;

    /**
     * 生成一个目标对象的代理对象
     *
     * @param object
     * @return
     */
    public Object getProxyObject(Object object) {
        this.targetObject = object;
        return Proxy.newProxyInstance(
                //类加载器
                targetObject.getClass().getClassLoader(),
                //获取目标对象接口
                targetObject.getClass().getInterfaces(),
                //InvocationHandler对象
                this);
    }


    /**
     * 代码增强
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据类生成的代理类不会有注解 要注意
        Object result = null;
        String classAnnoName = null;
        String methodAnnoName = null;
        //类上注解
        CustomAsync classCustomAsync = targetObject.getClass().getDeclaredAnnotation(CustomAsync.class);
        //方法上注解
        Method targetMethod = targetObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        CustomAsync methodCustomAsync = targetMethod.getDeclaredAnnotation(CustomAsync.class);
        ThreadPoolExecutor executor = null;
        if (!Objects.isNull(classCustomAsync) || !Objects.isNull(methodCustomAsync)) {
            classAnnoName = classCustomAsync==null?null:classCustomAsync.name();
            methodAnnoName = methodCustomAsync==null?null:methodCustomAsync.name();
            if(methodAnnoName!=null){
                System.out.println("方法"+method.getName()+"上有异步注解");
                executor = ContextUtil.getExecutor(methodAnnoName);
            }else if(classAnnoName!=null){
                System.out.println("类"+targetObject.getClass().getName()+"上有异步注解");
                executor = ContextUtil.getExecutor(classAnnoName);
            }
            System.out.println("执行异步操作");
//            Thread aa = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    if(true)
//                        throw new RuntimeException("xxxx");
//                }
//            });
//            executor.execute(aa);
            result = executor.submit(new ThreadTask(targetObject,method,args));
//            executor.shutdown();
        } else {
            result = method.invoke(targetObject, args);
        }
        return result;
    }
}

class ThreadTask implements Callable<String> {

    private Object targetObject;
    private Method method;
    private Object[] args;

    public ThreadTask(Object targetObject,Method method,Object[] args){
        this.targetObject = targetObject;
        this.method = method;
        this.args = args;
    }

    @Override
    public String call() throws Exception {
        Future<String> result = null;
        try {
            result = (Future<String>) method.invoke(targetObject, args);
        } catch (InvocationTargetException e) {
            try {
                throw e.getCause();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        return result.get(10, TimeUnit.SECONDS);
        return result.get();
    }
}


