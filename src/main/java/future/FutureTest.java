package future;

import ThreadPool.ThreadPool;

import java.util.concurrent.TimeUnit;

/**
 * @author 邱星晨
 */
public class FutureTest {
    public static void main(String[] args) throws InterruptedException {
        FutureService<Void, Void> service = FutureService.newService();
        Future<?> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("done");
        });
        future.get();
    }
}

class FutureTest2 {
    public static void main(String[] args) throws InterruptedException {
        FutureService<Integer, Integer> service = FutureService.newService();
        ThreadPool threadPool = service.getThreadPool();
        for (int i = 10000; i < 100000; i++) {
            Future<Integer> future = service.submit(input -> {
                int ans = 0;
                for (int j = 0; j < input; j++) {
                    ans += j;
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ans;
            }, i, System.out::println);
        }
        /**
         * 监控
         */
        while (true) {
            System.out.println("ActiveCount:" + threadPool.getActiveCount());
            System.out.println("QueueSize:" + threadPool.getQueueSize());
            System.out.println("CoreSize:" + threadPool.getCoreSize());
            System.out.println("MaxSize:" + threadPool.getMaxSize());
            TimeUnit.SECONDS.sleep(3);
        }
    }
}


class a {
    public static void main(String[] args) throws InterruptedException {
        th q=new th();
        Thread ts = new Thread(() -> {
            q.setTh(Thread.currentThread());
            while(true){

            }
        },"hhhhhhhhhh");
        ts.start();
        TimeUnit.SECONDS.sleep(1);
        q.interupt();
        System.out.println(ts.isInterrupted());
    }
}

class th {
    Thread th;
    String name;

    public th() {
    }

    public Thread getTh() {
        return th;
    }

    public void setTh(Thread th) {
        this.th = th;
        System.out.println(this.th.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void interupt(){
        this.th.interrupt();
    }
}