import context.ContextMap;
import context.ContextUtils;
import service.UserService;
import util.FileUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 模拟springioc加载
 */
public class SpringIocMain {

    private static Map<String,Object> beanMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        //1.读取配置文件 创建bean以及bean与bean的依赖关系 返回一个map key为beanid value为bean实例
        Map<String,Object> beanMap = FileUtil.readFileIntoContext("applicationContext.xml");
        printMap(beanMap);
        //2.将配置文件中的bean放入容器中
        ContextMap.getInstance().putAll(beanMap);

        //3.从容器中获取userService，并调用其sleepAndPlay方法
        UserService userService = (UserService) ContextUtils.getBean("userService");
        userService.sleepAndPlay();
    }



    private static void printMap(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> next = it.next();
            System.out.println(next.getKey()+":"+next.getValue().toString());
        }
        System.out.println("\n");
    }
}
