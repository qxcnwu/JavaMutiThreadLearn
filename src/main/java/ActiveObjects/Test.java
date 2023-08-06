package ActiveObjects;

import future.Future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author qxc
 * @version 1.0
 * @date 2023 2023/6/19 21:36
 * @see ActiveObjects
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("---------------------------------------------------");
        // 动态代理方式
        OrderService orderService = ActiveServiceFactory.active(new OrderServiceImpl());
        orderService.setOrder("订单名称12", "订单编号123");

        System.out.println(" 动态代理方式 是否继续执行下面的代码 ");
        Future<String> future2 = orderService.getOrder(1234L);
        String result2 = future2.get();
        System.out.println("动态代理方式 加工结果：" + result2);
    }
}

class t {
    public static void main(String[] args) throws InterruptedException {
        OrderService orderService = OrderServiceFactory.toActiveObject(new OrderServiceImpl());
        orderService.order("测试1133", 121212);
        System.out.println(" 是否继续执行下面的代码 ");
        Future<String> future = orderService.findOrderDetils(131313);
        String result = future.get();
        System.out.println("加工结果：" + result);
        System.out.println("---------------------------------------------------");
    }
}