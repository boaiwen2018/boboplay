package com.guanaitong.util;

import com.guanaitong.advice.*;
import com.guanaitong.anno.*;
import com.guanaitong.proxy.JdkProxyFactory;
import org.reflections.Reflections;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;


public class ContextBean {

    /**
     * 1.读取base-package并开始扫包
     * 2.扫描@Aspect切面注解,将切面中的切点(MyLog)与增强类型进行绑定  即能知道哪个注解需要哪些增强
     * 3.扫描Bean注解,判断Bean中的method是否有切点(MyLog)的注解,如果有则创建代理类(代理类中传入目标Object和增强类型)
     *
     * @return
     */
    public Map<String, Object> initBean() {
        Map<String, Object> beanMap = new HashMap<>();
        Map<String, Advisors> aspectMap = new HashMap<>();
        try {
            URL url = this.getClass().getClassLoader().getResource("applicationContext.xml");
            File f = new File(url.getFile());

            //读取并解析applicationContext.xml获取扫包路径
            String scanPackage = readXML(f);

            //通过扫包路径 获取Compoent的class
            Reflections reflections = new Reflections(scanPackage);

            //获取所有切面放入aspectMap中key为切点，value为具体的通知
            generateAspectMap(reflections,aspectMap);

            //初始化容器，将bean放入容器中
            //判断Bean中的method是否有切点(MyLog)的注解,如果有则创建代理类(代理类中传入目标对象和增强类型)
            initContext(reflections,aspectMap,beanMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanMap;
    }


    /**
     * 初始化容器
     * @param reflections
     * @param aspectMap
     * @param beanMap
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void initContext(Reflections reflections, Map<String, Advisors> aspectMap, Map<String, Object> beanMap) throws IllegalAccessException, InstantiationException {
        //找出包下有注解Service
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(MyServiceAnno.class);

        //为有切面注解的方法的类生成代理类 并为代理类的方法进行增强
        for (Class clazz : serviceClasses) {
            //定义哪些方法需要哪些通知
            Map<String,Advisors> methodAdvisorMap = new HashMap<>();

            boolean proxyFlag = false;
            //Bean的方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //获取方法上的注解
                Annotation[] annotations = method.getDeclaredAnnotations();
                //判断方法上的注解是不是有切面的注解 如果有就生成代理类
                for (Annotation annotation : annotations) {
                    if(aspectMap.containsKey(annotation.annotationType().getName())){
                        methodAdvisorMap.put(method.getName(),aspectMap.get(annotation.annotationType().getName()));
                        proxyFlag = true;
                    }
                }
            }

            Object obj = clazz.newInstance();
            if(proxyFlag) {
                obj = new JdkProxyFactory().createProxyInstance(obj, methodAdvisorMap);
            }

            Class beanClass = null;
            if(clazz.getInterfaces()!=null &&clazz.getInterfaces().length>0){
                beanClass = clazz.getInterfaces()[0];
            }else{
                beanClass = clazz;
            }
            beanMap.put(toLowerCaseFirstOne(beanClass.getName().substring(beanClass.getName().lastIndexOf(".")+1)), obj);
        }
    }

    /**
     * 创建切点与通知的关系
     * @param reflections
     * @param aspectMap
     */
    private void generateAspectMap(Reflections reflections,Map<String,Advisors> aspectMap) throws IllegalAccessException, InstantiationException {
        //获取所有切面
        Set<Class<?>> aspectClasses = reflections.getTypesAnnotatedWith(MyAspectAnno.class);

        //获取切点和增强模式 切点key为 anno的名字value为增强集合
        for (Class clazz : aspectClasses) {
            String pointcutMethodName = null;
            Advisors advisors = new Advisors();
            Map<String, String> annoMethodMap = new HashMap<>();
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                //如果是MyPointcutAnno切点注解
                if (method.isAnnotationPresent(MyPointcutAnno.class)) {
                    MyPointcutAnno myAnnotation = method.getAnnotation(MyPointcutAnno.class);
                    String aspectAnno = myAnnotation.value().replace("@annotation(","").replace(")","");
                    pointcutMethodName = method.getName();
                    aspectMap.put(aspectAnno, advisors);
                    annoMethodMap.put(pointcutMethodName, aspectAnno);
                }
            }

            for(Method method : methods){

                if (method.isAnnotationPresent(MyBefore.class)) {
                    MyBefore myAnnotation = method.getAnnotation(MyBefore.class);
                    String value = myAnnotation.value();
                    if (value.replace("()","").equals(pointcutMethodName)) {
                        String aspectAnno = annoMethodMap.get(value.replace("()",""));
                        advisors.setBeforeAdvisor(new BeforeAdvisor(clazz.newInstance(), method));
                        aspectMap.put(aspectAnno, advisors);
                    }
                }

                if (method.isAnnotationPresent(MyAfter.class)) {
                    MyAfter myAnnotation = method.getAnnotation(MyAfter.class);
                    String value = myAnnotation.value();
                    if (value.equals(pointcutMethodName+"()")) {
                        String aspectAnno = annoMethodMap.get(value.replace("()",""));
                        advisors.setAfterAdvisor(new AfterAdvisor(clazz.newInstance(), method));
                        aspectMap.put(aspectAnno, advisors);
                    }
                }


                if (method.isAnnotationPresent(MyAround.class)) {
                    MyAround myAnnotation = method.getAnnotation(MyAround.class);
                    String value = myAnnotation.value();
                    if (value.equals(pointcutMethodName+"()")) {
                        String aspectAnno = annoMethodMap.get(value.replace("()",""));
                        advisors.setAroundAdvisor(new AroundAdvisor(clazz.newInstance(), method));
                        aspectMap.put(aspectAnno, advisors);
                    }
                }

                if (method.isAnnotationPresent(MyAfterReturn.class)) {
                    MyAfterReturn myAnnotation = method.getAnnotation(MyAfterReturn.class);
                    String value = myAnnotation.value();
                    if (value.equals(pointcutMethodName+"()")) {
                        String aspectAnno = annoMethodMap.get(value.replace("()",""));
                        advisors.setAfterReturnAdvisor(new AfterReturnAdvisor(clazz.newInstance(), method));
                        aspectMap.put(aspectAnno, advisors);
                    }
                }

                if (method.isAnnotationPresent(MyAfterThrowing.class)) {
                    MyAfterThrowing myAnnotation = method.getAnnotation(MyAfterThrowing.class);
                    String value = myAnnotation.value();
                    if (value.equals(pointcutMethodName+"()")) {
                        String aspectAnno = annoMethodMap.get(value.replace("()",""));
                        advisors.setAfterThrowingAdvisor(new AfterThrowingAdvisor(clazz.newInstance(), method));
                        aspectMap.put(aspectAnno, advisors);
                    }
                }
            }
        }
        System.out.println("aspectMap="+aspectMap);
    }


    private String readXML(File f) throws IOException, SAXException, ParserConfigurationException {
        //读取xml文件
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);

        //解析xml文件
        NodeList componentScan = doc.getElementsByTagName("component-scan");
        String scanPackage = null;
        if (componentScan.getLength() > 0) {
            org.w3c.dom.Node bean = componentScan.item(0);
            //获取bean的属性
            NamedNodeMap beanAttributes = bean.getAttributes();
            //获取bean id值
            scanPackage = beanAttributes.getNamedItem("base-package").getNodeValue();
        }
        return scanPackage;
    }


    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }

    }

}
