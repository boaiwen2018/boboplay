import service.UserService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Java8Main {

    public static void main(String[] args) {

        UserService userService = new UserService();

        System.out.println("打印所有员工信息");
        System.out.println(userService.getAllUser()+"\n");

        System.out.println("获取所有员工的名字");
        printList(userService.getAllUserName());

        System.out.println("是否存在女员工");
        printString(true == userService.hasFemaleInAllUser()?"是":"否");


        System.out.println("获取所有男员工的名字");
        printList(userService.getAllMaleUserName());

        System.out.println("获取按年龄倒序排列后的员工列表");
        System.out.println(userService.getUserOrderByAgeDesc()+"\n");


        System.out.println("将所有员工得姓名前加上企业名称前缀 (关爱通_) 后返回员工姓名列表");
        printList(userService.getUserNameForAddEnterpriseName());

        System.out.println("统计所有员工中 男员工与女员工的总数");
        printMap(userService.getCountBySex());


        System.out.println("将所有员工按每组2人进行分组");
        System.out.println(userService.groupByNumberForAllUser(2)+"\n");

        System.out.println("将所有员工按每组1人进行分组");
        System.out.println(userService.groupByNumberForAllUser(1)+"\n");

    }

    private static void printMap(Map<String, Long> map) {
        Iterator<Map.Entry<String, Long>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Long> next = it.next();
            System.out.println(next.getKey()+":"+next.getValue());
        }
        System.out.println("\n");
    }


    public static void printList(List<String> stringList){
        stringList.forEach(user->System.out.println(user+" "));
        System.out.println("\n");
    }

    public static void printString(String str){
        System.out.println(str+"\n");
    }



}
