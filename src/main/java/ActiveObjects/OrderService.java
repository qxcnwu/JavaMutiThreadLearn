package ActiveObjects;

import future.Future;
import future.FutureService;

import java.util.concurrent.TimeUnit;

/**
 * 方法接口命名
 *
 * @author 邱星晨
 */
public interface OrderService {
    /**
     * 有返回参数的 异步操作只能先返回凭据信息给调用者  通过id查找订单信息
     */
    Future<String> findOrderDetils(long orderId);

    /**
     * 没有返回值  提交订单
     * @param account
     * @param orderId
     */
    void order(String account,long orderId);

    /**
     * 动态代理的方式实现
     * @param orderId
     * @return
     */
    @ActiveMethod
    default Future<String> getOrder(Long orderId) {
        return FutureService.<Long,String>newService().submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("动态代理方式获取订单信息中......... 信息为："+orderId);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            return "query1234";
        },orderId,null);
    }

    @ActiveMethod
    default void setOrder(String name,String orderNo){
        System.out.println("动态代理方式提交订单中........信息为： "+name+","+orderNo);
    }
}

