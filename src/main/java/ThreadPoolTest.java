import ThreadPool.ThreadPool;
import ThreadPool.BasicThreadPool;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main2(String[] args) throws InterruptedException {
        final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(20);
                    System.out.println(Thread.currentThread().getName() + " is running and done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (; ; ) {
            System.out.println("ActiveCount:" + threadPool.getActiveCount());
            System.out.println("QueueSize:" + threadPool.getQueueSize());
            System.out.println("CoreSize:" + threadPool.getCoreSize());
            System.out.println("MaxSize:" + threadPool.getMaxSize());
            TimeUnit.SECONDS.sleep(3);
        }
        //TimeUnit.SECONDS.sleep(40);
        //threadPool.shutdown();
    }

    public static void main(String[] args) {
//        ThreadPoolExecutor s = new ThreadPoolExecutor();
    }
}
