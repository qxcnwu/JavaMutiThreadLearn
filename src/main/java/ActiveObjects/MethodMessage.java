package ActiveObjects;

import java.util.Map;

/**
 * 收集每个接口的方法参数
 * 类似 流水线设计模式中的半产品信息 其中execute方法相当加工方法
 */
public abstract class MethodMessage {
    // 收集的方法参数 返回future类型数据一样收集
    protected final Map<String,Object> params;

    protected final OrderService orderService;

    protected MethodMessage(Map<String, Object> params, OrderService orderService) {
        this.params = params;
        this.orderService = orderService;
    }
    // execute方法 加工方法
    public abstract void execute();
}