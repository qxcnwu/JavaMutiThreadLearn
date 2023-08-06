import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * volatile原子性操作其基础是 jvm的内存结构
 * 对于一个多线程应用其将有交集的变量 通过类似缓存一致性的原则
 * 当某个线程争抢到锁以后其他线程内此变量无效，需要使用的时候在
 * 从内存里面读取
 */
public class VolatileLearn {
    final static int a = 5;
    volatile static int init = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            int value = init;
            while (init < a) {
                if (init != value) {
                    System.out.println("value be update to " + init);
                }
                value = init;
            }
        }, "reader1").start();

        new Thread(() -> {
            int value = init;
            while (init < a) {
                System.out.println("value be change to " + (++value));
                init = value;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "reader2").start();
    }
}

/**
 * 多线程需要保证程序的 原子性 有序性 可见性
 * 原子性：多次操作需要都完成 类似于JDBC里面的时间 多个原子性操作组合不能保证原子性
 * 可见性：一个线程对共享变量操作，其他线程能够查找
 * 有序性：代码重新排列
 * <p>
 * 原子性只在变量赋值的时候存在
 * 可见性 volatile,synchronized,lock 后两者通过同步完成、前面通过
 */
class prop {
    private static volatile Integer a = 1;
    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    public synchronized static void inc() {
        a++;
    }

    /**
     * volatile 不保证原子性
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a);
    }
}
