package com.guanaitong.springioc.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


public class FileUtil {

    private static final String PATH = "src/main/java/com/guanaitong/springioc/";

    public static Map<String,Object> readFileIntoContext(String fileName){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            //读取xml文件
            File f = new File(PATH+fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            //获取bean标签 并实例化bean
            NodeList beans = doc.getElementsByTagName("bean");
            for(int i = 0; i < beans.getLength(); i++){
                //获取bean节点
                org.w3c.dom.Node bean = beans.item(i);
                //获取bean的属性
                NamedNodeMap beanAttributes = bean.getAttributes();
                //获取bean id值
                String beanId = beanAttributes.getNamedItem("id").getNodeValue();
                //获取bean class值
                String beanClass = beanAttributes.getNamedItem("class").getNodeValue();

                if(beanId != null && beanClass !=null){
                    //通过反射创建class对象
                    Class clazz = Class.forName(beanClass);
                    //使用clazz对应类的默认构造器创建实例
                    Object obj = clazz.newInstance();
                    returnMap.put(beanId,obj);
                }
            }


            //获取property标签
            NodeList properties = doc.getElementsByTagName("property");
            for(int i = 0; i < properties.getLength(); i++){
                //获取property节点
                org.w3c.dom.Node property = properties.item(i);
                org.w3c.dom.Node parentNode = property.getParentNode();
                //获取property的属性
                NamedNodeMap beanAttributes = property.getAttributes();
                //获取property name值
                String beanName = beanAttributes.getNamedItem("name").getNodeValue();
                //获取property class值
                String beanRef = beanAttributes.getNamedItem("ref").getNodeValue();
                //获取父亲节点bean id
                String parentId = parentNode.getAttributes().getNamedItem("id").getNodeValue();
                //通过id获取被注入对象
                Object obj = returnMap.get(parentId);
                //通过id获取注入对象
                Object obj2 = returnMap.get(beanRef);
                //将依赖对象注入
                Method[] method = obj.getClass().getDeclaredMethods();
                for (int n = 0; n < method.length; n++) {
                    String name = method[n].getName();
                    String temp = null;
                    if (name.startsWith("set")) {
                        temp = toLowerCaseFirstOne(name.substring(3, name.length()));
                        if (obj2 != null) {
                            if (temp.equals(beanRef)) {
                                method[n].invoke(obj, obj2);
                                returnMap.put(parentId,obj);
                            }
                        }
                    }
                }
            }
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
