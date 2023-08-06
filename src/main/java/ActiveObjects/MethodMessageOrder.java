package ActiveObjects;

import java.util.Map;

/**
 * order 提交方法的封装类
 * @author 邱星晨
 */
public class MethodMessageOrder extends MethodMessage{

    public MethodMessageOrder(Map<String,Object> params, OrderService orderService){
        super(params, orderService);
    }
    @Override
    public void execute() {
        // 获取方法参数 执行对应方法
        orderService.order((String) params.get("account"),(Long) params.get("orderId"));
    }
}

