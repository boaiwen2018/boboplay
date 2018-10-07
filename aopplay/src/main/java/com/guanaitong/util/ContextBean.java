package com.guanaitong.util;

import com.guanaitong.anno.MyAspectAnno;
import com.guanaitong.anno.MyServiceAnno;
import org.reflections.Reflections;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ContextBean {

    public Map<String,Object> initBean(){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            URL url = this.getClass().getClassLoader().getResource("applicationContext.xml");
            //读取xml文件
            File f = new File(url.getFile());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            //解析xml文件
            NodeList componentScan = doc.getElementsByTagName("component-scan");
            String scanPackage = null;
            if(componentScan.getLength()>0){
                org.w3c.dom.Node bean = componentScan.item(0);
                //获取bean的属性
                NamedNodeMap beanAttributes = bean.getAttributes();
                //获取bean id值
                scanPackage = beanAttributes.getNamedItem("base-package").getNodeValue();
            }

            //通过扫包路径 注册serviceBean和AspectBean
            Reflections reflections = new Reflections(scanPackage);
            //找出包下有注解Service和aspect的class
            Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(MyServiceAnno.class);
            Set<Class<?>> aspectClasses = reflections.getTypesAnnotatedWith(MyAspectAnno.class);

            for(Class clazz : serviceClasses) {
                System.out.println("Found service: " + clazz.getName());
                //使用clazz对应类的默认构造器创建实例
                Object obj = clazz.newInstance();
                returnMap.put(toLowerCaseFirstOne(clazz.getName()),obj);
            }

            for(Class clazz : aspectClasses) {
                System.out.println("Found aspect: " + clazz.getName());
            }



            //获取property标签
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
        return returnMap;
    }


    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


}
