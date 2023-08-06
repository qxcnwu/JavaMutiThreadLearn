import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadLearn {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new A()).start();
            new Thread(new B()).start();
            new Thread(C::tell).start();
        }
        IntStream.range(0, 5).mapToObj(ThreadLearn::a).forEach(Thread::start);
    }

    public static Thread a(Integer num) {
        return new Thread(new A());
    }
}

class A extends Thread {
    @Override
    public void run() {
        for (; ; ) {
            System.out.println(A.class.getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class B extends Thread {
    @Override
    public void run() {
        for (; ; ) {
            System.out.println(B.class.getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class C {
    public static void tell() {
        System.out.println("tell");
    }

    public void say() {
        System.out.println("say");
    }
}

class th {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup sp = new ThreadGroup(Thread.currentThread().getThreadGroup(), "");
        Thread th = new Thread(sp, new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Mth");
        th.start();
        TimeUnit.SECONDS.sleep(2);
        ThreadGroup sp2 = new ThreadGroup(sp, "");
        Thread[] ts = new Thread[10];
        final int enumerate = sp.enumerate(ts, true);
        System.out.println(enumerate);
        System.out.println(Arrays.toString(ts));
        sp.list();
        System.out.println(sp.parentOf(sp));
        //设置守护线程组用于后台多任务,主线程的线程组全部退出，同时退出，没有活跃线程自动退出
        Thread.currentThread().getThreadGroup().setDaemon(true);
        //没有活跃线程的线程组被销毁
        Thread.currentThread().getThreadGroup().destroy();
        //递归打断所有线程
        Thread.currentThread().getThreadGroup().interrupt();
    }
}

class th2 {
    public static void main1(String[] args) {
        // 设置异常处理方式
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                e.printStackTrace();
            }
        });
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {

                }
                System.out.println(1 / 0);
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        // 可以注册多个钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("bye");
            }
        }));
        // 可以注册多个钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("bye2");
            }
        }));
    }
}