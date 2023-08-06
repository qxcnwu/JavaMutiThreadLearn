import javax.print.attribute.standard.RequestingUserName;
import java.util.concurrent.TimeUnit;

/**
 * @Author qxc
 * @Date 2023 2023/6/12 17:29
 * @Version 1.0
 * @PACKAGE PACKAGE_NAME
 */
public class ThreadConstruction {
    /**
     * stacksize在windows平台上影响非常诡异，影响帝圭省督
     *
     * @param args
     */
    public static void main2(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("father");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                recurse(i);
            }

            public void recurse(int i) {
                while (i < 9000) {
                    recurse(i + 1);
                }
            }
        };
        System.out.println(Thread.currentThread().getId());
        System.out.println(Thread.currentThread().isDaemon());
        Thread th = new Thread(group, runnable, "hh", 0);
        Thread.sleep(1000);
        th.setPriority(10);
        TimeUnit.SECONDS.sleep(10);
//        设置为守护线程
        th.setDaemon(true);
        th.start();
    }

    public static void main3(String[] args) {
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println(Thread.currentThread().getId());
        Thread.currentThread().interrupt();
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        try {
            TimeUnit.MICROSECONDS.sleep(1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main22(String[] args) throws InterruptedException {
        Thread th = new Thread(() -> {
            System.out.println("start1");
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i + " s1");
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("1 done");
        });
        Thread th2 = new Thread(() -> {
            System.out.println("start2");
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i + " s2");
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("2 done");
        });
        th.start();
        th2.start();

        th.join();
        System.out.println("okks");
        th2.join();

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println(i);
                }
                System.out.println(100);
            }
        });
        th.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(th.isAlive());
        for (int i = 0; i < 100; i++) {
            th.join();
            System.out.println(Thread.currentThread().isInterrupted());
        }
        System.out.println(th.isAlive());
    }
}
