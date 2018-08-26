package com.guanaitong.async.util;

import com.guanaitong.async.anno.CustomAsync;
import com.guanaitong.async.proxy.AsyncProxy;
import com.guanaitong.exception.CustomExceptionHandler;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


public class ContextUtil {

    public static String defaultExecutor;
    public static Map<String, Object> beanMap = new HashMap<>();

    public static Map<String, Object> loadBean(String fileName) {
        try {
            //读取xml文件
            String fileUrl = ClassLoader.getSystemResource(fileName).getFile();
            File f = new File(fileUrl);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            putBean(beanMap, doc);
            putExecutors(beanMap, doc);

//            //获取property标签
//            NodeList properties = doc.getElementsByTagName("property");
//            for(int i = 0; i < properties.getLength(); i++){
//                //获取property节点
//                org.w3c.dom.Node property = properties.item(i);
//                org.w3c.dom.Node parentNode = property.getParentNode();
//                //获取property的属性
//                NamedNodeMap beanAttributes = property.getAttributes();
//                //获取property name值
//                String beanName = beanAttributes.getNamedItem("name").getNodeValue();
//                //获取property class值
//                String beanRef = beanAttributes.getNamedItem("ref").getNodeValue();
//                //获取父亲节点bean id
//                String parentId = parentNode.getAttributes().getNamedItem("id").getNodeValue();
//                //通过id获取被注入对象
//                Object obj = returnMap.get(parentId);
//                //通过id获取注入对象
//                Object obj2 = returnMap.get(beanRef);
//                //将依赖对象注入
//                Method[] method = obj.getClass().getDeclaredMethods();
//                for (int n = 0; n < method.length; n++) {
//                    String name = method[n].getName();
//                    String temp = null;
//                    if (name.startsWith("set")) {
//                        temp = toLowerCaseFirstOne(name.substring(3, name.length()));
//                        if (obj2 != null) {
//                            if (temp.equals(beanRef)) {
//                                method[n].invoke(obj, obj2);
//                                returnMap.put(parentId,obj);
//                            }
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanMap;
    }


    private static void putExecutors(Map<String, Object> beanMap, Document doc) {
        NodeList executors = doc.getElementsByTagName("executor");
        for (int i = 0; i < executors.getLength(); i++) {
            org.w3c.dom.Node bean = executors.item(i);
            NamedNodeMap beanAttributes = bean.getAttributes();
            String beanId = beanAttributes.getNamedItem("id").getNodeValue();
            String poolSize = beanAttributes.getNamedItem("pool-size").getNodeValue();
            if (beanId != null) {

                ThreadFactory executorThreadFactory = new BasicThreadFactory.Builder()
                        .namingPattern("task-scanner-executor-%d")
                        .uncaughtExceptionHandler(new CustomExceptionHandler())
                        .build();

                beanMap.put(beanId, new ThreadPoolExecutor(Integer.valueOf(poolSize), 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),executorThreadFactory));
//                beanMap.put(beanId, Executors.newCachedThreadPool());
                defaultExecutor = beanId;
            }
        }
    }

    private static void putBean(Map<String, Object> beanMap, Document doc) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //获取bean标签 并实例化bean
        NodeList beans = doc.getElementsByTagName("bean");
        for (int i = 0; i < beans.getLength(); i++) {
            //获取bean节点
            org.w3c.dom.Node bean = beans.item(i);
            //获取bean的属性
            NamedNodeMap beanAttributes = bean.getAttributes();
            //获取bean id值
            String beanId = beanAttributes.getNamedItem("id").getNodeValue();
            //获取bean class值
            String beanClass = beanAttributes.getNamedItem("class").getNodeValue();

            if (beanId != null && beanClass != null) {
                //通过反射创建class对象
                Class clazz = Class.forName(beanClass);
                //使用clazz对应类的默认构造器创建实例
                Object obj = clazz.newInstance();
                CustomAsync customAsync = (CustomAsync) clazz.getDeclaredAnnotation(CustomAsync.class);
                if (customAsync != null) {
                    System.out.println("类上有注解,创建"+clazz.getName()+"类的代理类");
                    obj = new AsyncProxy().getProxyObject(obj);
                }else{
                    for (Method method : clazz.getDeclaredMethods()) {
                        CustomAsync methodAnnotation = method.getAnnotation(CustomAsync.class);
                        if (methodAnnotation != null) {
                            //如果存在注解则生成代理类
                            obj = new AsyncProxy().getProxyObject(obj);
                        }
                    }
                }
                beanMap.put(beanId, obj);
            }
        }
    }


    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 根据beanId获取bean
     *
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        return beanMap.get(beanId);
    }

    public static ThreadPoolExecutor getExecutor(String executorName) {
        if (beanMap.get(executorName) == null) {
            executorName = defaultExecutor;
        }
        return (ThreadPoolExecutor) beanMap.get(executorName);
    }
}
