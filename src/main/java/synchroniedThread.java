import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @author 邱星晨
 */
public class synchroniedThread {
    public void test() {
        synchronized ("a") {
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        synchroniedThread thd = new synchroniedThread();
        for (int i = 0; i < 10; i++) {
            new Thread(thd::test).start();
        }
    }
}


/**
 * 一个类中如果有两个静态方法被synchronized修饰,那么他们的monitor是同一个即是类对象
 */
class testMonitor {
    public static void main(String[] args) {
        testMonitor testMonitors = new testMonitor();
//        new Thread(testMonitors::func1, "10 ").start();
//        new Thread(testMonitors::func2, "1 ").start();

        new Thread(testMonitor::method1, "10 ").start();
        new Thread(testMonitor::method2, "1 ").start();
    }

    public synchronized void func1() {
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void func2() {
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void method1() {
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void method2() {
        synchronized (testMonitor.class) {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

/**
 * 死锁的原因
 * 1. 交叉锁
 * 2.内存不足
 * 3.一问一答数据交换
 * 4.数据库锁
 * 5.文件锁
 * 6.死循环死锁
 */
class DeadLock {
    private final Object aObj = new Object();
    private final Object bObj = new Object();

    public void read() {
        synchronized (aObj) {
            System.out.println("get read obj");
            synchronized (bObj) {
                System.out.println("get write obj");
            }
            System.out.println("release write");
        }
        System.out.println("release read");
    }

    public void write() {
        synchronized (bObj) {
            System.out.println("get write obj");
            synchronized (aObj) {
                System.out.println("get read obj");
            }
            System.out.println("release read");
        }
        System.out.println("release write");
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        new Thread(() -> {
            while (true) {
                deadLock.read();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                deadLock.write();
            }
        }).start();

    }
}

class eventQueue {
    private final int max;
    private final static int defaultMax = 10;

    static class Event {

    }

    private final LinkedList<Event> eventQueueL = new LinkedList<>();

    public eventQueue() {
        this(defaultMax);
    }

    public eventQueue(int max) {
        this.max = max;
    }

    public void offer(Event event) {
        synchronized (eventQueueL) {
            if (eventQueueL.size() >= max) {
                try {
                    System.out.println("queue is full");
                    eventQueueL.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("submit new event");
            eventQueueL.addLast(event);
            eventQueueL.notifyAll();
        }
    }

    public Event take() {
        synchronized (eventQueueL) {
            while (eventQueueL.isEmpty()) {
                System.out.println("empty");
                try {
                    eventQueueL.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Event event = eventQueueL.removeFirst();
            eventQueueL.notifyAll();
            System.out.println("taken event" + event);
            return event;
        }
    }
}

/**
 * notify和wait是每个类都具有的方法
 * notify唤醒因为当前对象而进入wait的线程
 * 唤醒执行当前对象wait的线程
 * 被唤醒线程需要继续获取mobitor锁才能继续执行
 * wait notify必须在一个synchronized中同时出现
 * 或者在同一个代码段中同时出现
 * 以上都是在单线程通讯是的代码
 */
class eventClient {
    public static void main(String[] args) {
        final eventQueue eq = new eventQueue();
        new Thread(() -> {
            for (int i = 0; i < 30; ++i) {
                eq.offer(new eventQueue.Event());
            }
        }, "p").start();
        new Thread(() -> {
            for (; ; ) {
                eq.take();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "q").start();
    }
}

/**
 * 解决多个线程同时访问使用notifyAll
 */
class eventClients {
    public static void main(String[] args) {
        final eventQueue eq = new eventQueue();
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                for (; ; ) {
                    eq.offer(new eventQueue.Event());
                }
            }, "p").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (; ; ) {
                    eq.take();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }, "q").start();
        }
    }
}
