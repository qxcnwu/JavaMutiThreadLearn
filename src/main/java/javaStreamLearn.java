import java.awt.image.ImageProducer;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class javaStreamLearn {
    public static void main(String[] args) {
        ArrayList<Integer> list=new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
            list.add(i);
        }
        // 集合转换为流
        list.stream();
        //并行流
        list.parallelStream();
        //流转集合 中间操作返回流
        List<Integer> answer=list.stream().filter(i->i<500).collect(Collectors.toList());
        System.out.println(answer);

        //去重 根据内存地址 equal hashcode函数判断
        answer=list.stream().distinct().collect(Collectors.toList());
        System.out.println(answer);

        ArrayList<E1> list1=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list1.add(new E1());
        }
        System.out.println(list1.stream().distinct().collect(Collectors.toList()));

        //limit 分页
        answer=list.stream().limit(2).collect(Collectors.toList());
        System.out.println(answer);

        //skip去掉前几个
        answer=list.stream().skip(1999).collect(Collectors.toList());
        System.out.println(answer);

        //map 接收函数参数 对所有元素进行处理
        answer=list.stream().map(i->i*2).skip(999).limit(10).distinct().collect(Collectors.toList());
        System.out.println(answer);
        // 函数
        answer=list.stream().map(i->{
            return i*3;
        }).skip(999).limit(10).distinct().collect(Collectors.toList());
        System.out.println(answer);

        //flatmap 各个数组 流的别平滑处理 压缩维度
        List<String> str= Arrays.asList("张三","里是","王五","adsasdasda","asdasdn","adasdasd");
        final List<String> list2 = str.stream().flatMap(String::lines).collect(Collectors.toList());
        System.out.println(list2);
        final List<Stream<String>> list3 = str.stream().map(String::lines).collect(Collectors.toList());
        System.out.println(list3);
        System.out.println(str.stream().sorted().collect(Collectors.toList()));
        //排序可以设置字符串大小，排序方式，当然还有反序
        // 自定义比较器需要在类继承Compareable接口
        System.out.println(str.stream().sorted(Collections.reverseOrder(Collator.getInstance(Locale.CHINA))).collect(Collectors.toList()));

        //==========================终止操作符==========================================
        //anymatch
        boolean b = list.stream().filter(i -> i > 30000).anyMatch(i -> i < 430000);
        System.out.println(b);

        b = list.stream().filter(i -> i > 0).allMatch(i -> i < 430000);
        System.out.println(b);

        b = list.stream().filter(i -> i < 111110).noneMatch(i -> i < -430000);
        System.out.println(b);

        //findany返回任意一个返回第一个
        Optional<Integer> integer = list.stream().filter(i -> i < 100).findAny();
        integer.ifPresent(System.out::println);
        //并行流可能返回的值不相同
        for (int i = 0; i < 1000; i++) {
            integer = list.parallelStream().filter(i1 -> i1 < 100).findAny();
            integer.ifPresent(System.out::println);
        }

        for (int i = 0; i < 1000; i++) {
            integer = list.stream().filter(i2 -> i2 < 100).findFirst();
            integer.ifPresent(System.out::println);
        }

        //foreach 访问者模式
        list.parallelStream().forEach(i->{
            i=i*2;
            System.out.println(i);
        });

        //collect 转换list,map,set
        Set<Integer> collect = list.stream().filter(i->i<10000).collect(Collectors.toSet());
        Map<Object, Object> collect1 = list.stream().collect(Collectors.toMap(v -> v, v -> v, (oldvalue, newvalue) -> newvalue));
        System.out.println(collect1);

        //reduce 累计处理 反复结合得到结果
        final Optional<Integer> reduce = list.stream().reduce(Integer::sum);
        System.out.println(reduce.get());

        // count获取集合中元素数量
        System.out.println(list.stream().count());
    }
}

class E1{
    private String name;
    private Integer age;

    public E1(){
        name="a";
        age=1;
    }

    public E1(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "E1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        E1 e1 = (E1) o;
        return Objects.equals(name, e1.name) && Objects.equals(age, e1.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
