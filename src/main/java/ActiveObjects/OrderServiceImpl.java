package ActiveObjects;

import future.Future;
import future.FutureService;

import java.util.concurrent.TimeUnit;

public class OrderServiceImpl implements OrderService {

    @Override
    public Future<String> findOrderDetils(long orderId) {
        return FutureService.<Long,String>newService().submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("获取订单信息中......... 信息为："+orderId);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            return "1234qwer";
        },orderId,null);
    }

    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("提交订单中........信息为： "+account+","+orderId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}