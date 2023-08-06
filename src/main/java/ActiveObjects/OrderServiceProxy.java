package ActiveObjects;



import future.Future;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口使用代理类
 * 将接口的每个方法封装成MethodMessage 然后交给ActiveMessage队列执行
 */
public class OrderServiceProxy implements OrderService {
    private final OrderService orderService;

    private final MethodMessageQueue methodMessageQueue;

    public OrderServiceProxy(OrderService orderService, MethodMessageQueue methodMessageQueue) {
        this.orderService = orderService;
        this.methodMessageQueue = methodMessageQueue;
    }

    @Override
    public Future<String> findOrderDetils(long orderId) {
        // 定义一个可以立刻返回的凭证
        final ActiveFuture<String> activeFuture = new ActiveFuture<>();
        // 收集方法参数信息 和返回值信息 封装成对应方法的MethodMessage
        Map<String,Object> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("activeFuture",activeFuture);
        MethodMessage message = new MethodMessageFind(params,orderService);
        // 将待加工的信息放进传送带
        methodMessageQueue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        // 收集参数
        Map<String,Object> params = new HashMap<>();
        params.put("account",account);
        params.put("orderId",orderId);
        MethodMessage message = new MethodMessageOrder(params,orderService);
        // 放入传送带
        methodMessageQueue.offer(message);
    }
}




