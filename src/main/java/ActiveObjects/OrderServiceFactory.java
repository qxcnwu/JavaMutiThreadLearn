package ActiveObjects;

/**
 * 代理工厂类
 * @author 邱星晨
 */
public final class OrderServiceFactory {
    // 用static修饰 保证JVM进程唯一
    private final static MethodMessageQueue activeQueue = new MethodMessageQueue();
    // 去除new方法
    private OrderServiceFactory(){}
    // 返回OrderService接口
    public static OrderService toActiveObject(OrderService orderService){
        return new OrderServiceProxy(orderService,activeQueue);
    }
}
