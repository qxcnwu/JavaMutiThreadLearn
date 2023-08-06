package Latch;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.currentThread;

/**
 * @author 邱星晨
 */
public class Test {
    public static void main(String[] args) {
        CountDownLatch countDownLatch=new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Random random=new Random();
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                    System.out.println(currentThread().getName()+":"+countDownLatch.getUnarrived());
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
        try {
            countDownLatch.await(TimeUnit.SECONDS,4);
            System.out.println("完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
