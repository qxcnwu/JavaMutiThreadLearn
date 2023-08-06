package ActiveObjects;

import future.Future;

import java.util.Map;

/**
 * findOrderDetils 方法的接口封装类
 */
public class MethodMessageFind extends MethodMessage{

    public MethodMessageFind(Map<String,Object> params, OrderService orderService){
        super(params,orderService);
    }

    @Override
    public void execute() {
        // 设置方法的参数信息 获取方法执行后凭证信息
        Future<String> realFuture = orderService.findOrderDetils((long) params.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");
        try {
            // 通过凭证获取结果并设置结果
            String result = realFuture.get();
            activeFuture.finish(result);
        } catch (InterruptedException e) {
            // 异常设置空值
            activeFuture.finish(null);
        }
    }
}