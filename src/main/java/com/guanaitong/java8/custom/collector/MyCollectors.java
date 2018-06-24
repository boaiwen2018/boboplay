package com.guanaitong.java8.custom.collector;


import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


//Collector中R
//T：stream在调用collect方法收集前的数据类型
//A：A是T的累加器，遍历T的时候，会把T按照一定的方式添加到A中，换句话说就是把一些T通过一种方式变成A
//R：R可以看成是A的累加器，是最终的结果，是把A汇聚之后的数据类型，换句话说就是把一些A通过一种方式变成R

//supplier就是生成容器，accumulator是添加元素，combiner是合并容器，finisher是输出的结果，characteristics是定义容器的三个属性，包括是否有明确的finisher，是否需要同步，是否有序。

//IDENTITY_FINISH   不用finisher，doc的描述为elided（可以省略的）
//UNORDERED         集合是无序的
//CONCURRENT        集合的操作需要同步

public class MyCollectors {

    // 默认采用2个一起分组
    public static <T> Collector<T, List<List<T>>, List<List<T>>> groupByNumber(){
        return MyCollectors.groupByNumber(2);
    }

    // 根据number的大小进行分组
    public static <T> Collector<T, List<List<T>>, List<List<T>>> groupByNumber(int number){
        return new NumberCollectorImpl(number);
    }

    /**
     * 个数分组器
     * @param <T>
     */
    static class NumberCollectorImpl<T> implements Collector<T, List<List<T>>, List<List<T>>> {
        // 每组的个数
        private int number;

        public NumberCollectorImpl(int number) {
            this.number = number;
        }

        @Override
        public Supplier<List<List<T>>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<List<T>>, T> accumulator() {
            return (list, item) -> {
                if (list.isEmpty()){
                    list.add(this.createNewList(item));
                }else {
                    List<T> last = (List<T>) list.get(list.size() - 1);
                    if (last.size() < number){
                        last.add(item);
                    }else{
                        list.add(this.createNewList(item));
                    }
                }
            };
        }

        @Override
        public BinaryOperator<List<List<T>>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }

        @Override
        public Function<List<List<T>>, List<List<T>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
        }

        private List<T> createNewList(T item){
            List<T> newOne = new ArrayList<T>();
            newOne.add(item);
            return newOne;
        }
    }
}