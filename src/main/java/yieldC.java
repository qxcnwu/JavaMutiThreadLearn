import com.sun.jdi.PathSearchingVirtualMachine;

import javax.swing.*;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class DeepThread {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("down");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("down");
    }

}

/**
 * 不能保证每次都执行Yield部分
 */
public class yieldC{
    public static void main(String[] args) {
        IntStream.range(0,800).mapToObj(yieldC::test).forEach(Thread::start);
    }

    public static Thread test(int index){
        return new Thread(()->
        {
            if(index==600){
                Thread.yield();
                try {
                    TimeUnit.SECONDS.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(index);
        });
    }
}

/**
 * 对于线程优先级在特定情况下会失效
 * 因此不可以过度依赖于优先级
 * 线程设置的优先级不能高于其说属于的线程组的优先级，反之将其取代
 *
 */
class ThreadSp{
    public static void main(String[] args) {
        // 线程优先级需要小于线程组的优先级
        ThreadGroup thg=new ThreadGroup("makeg");
        thg.setMaxPriority(7);
        Thread thd=new Thread(thg,()->{
            System.out.println("线程设置优先级为10");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thd.setPriority(10);
        System.out.println(thd.getPriority());
        thd.start();
    }
}

class ThreadID{
    public static void main(String[] args) {
        int i=0;
        for(;;){
            Thread thd=new Thread("a"+i++);
            System.out.println(thd.getId());
            thd.start();
        }
    }
}

/**
 * 设置线程上下文类加载器
 * getContextClassLoader 此线程由哪一个类加载
 * setContextClassLoader() 设置线程的类加载器
 */
class classLoaderCls{
    // 略
}

/**
 * Interrupt使得当前线程阻塞
 * 线程如果死亡则对其Interrupt无效
 * 线程被中断设置Interrupt flag
 * 可中断线程被中断导致flag清除 如果  Interrupt中含有sleep操作
 * 则其中的Interrupte会被查出
 * 如果一个线程被中断那么其可中断方法会被立即中断
 *
 */
class ThdInterrupt{
    public static void main(String[] args) throws InterruptedException {
        Thread thd=new Thread(()->{
            int i=0;
            while(true){
                i++;
            }
        });
        thd.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println(thd.isInterrupted());
        thd.interrupt();
        System.out.println(thd.isInterrupted());
    }
}

/**
 * join方法 join线程会使其他线程进入等待
 */
class ThreadJoin{
    public static void main(String[] args) {
        List<Thread> list=IntStream.range(0,2).mapToObj(ThreadJoin::create).collect(Collectors.toList());
        list.forEach(Thread::start);
        list.forEach(i->{
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < 10; i++) {
            System.out.println("main:"+i);
            shortcut();
        }
        list.forEach(i->{
            System.out.println("join");
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < 10; i++) {
            System.out.println("main:"+ ThreadLocalRandom.current().nextInt(10));
            shortcut();
        }
    }

    public static Thread create(Integer integer){
        return new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(integer+":"+i);
                shortcut();
            }
        });
    }

    public static void shortcut(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程正常结束。退出生命周期
 * interrupt捕获中断信号关闭线程
 * Volatail开关控制
 */
class ThreadStop{
    public static void main(String[] args) throws InterruptedException {
        Thread thd=new Thread(){
            @Override
            public void run() {
                while (!isInterrupted()) {

                }
                System.out.println("exitting!!");
            }
        };
        thd.start();
        TimeUnit.SECONDS.sleep(5);
        thd.interrupt();

        B b=new B();
        Thread th=new Thread(b);
        th.start();
        TimeUnit.SECONDS.sleep(3);
        b.close();

    }

    static class B extends Thread{
        private volatile boolean closed=false;

        @Override
        public void run() {
            System.out.println("work");
            while(!closed&&!isInterrupted()){

            }
            System.out.println("exited");
        }

        public void close(){
            closed=true;
            this.interrupt();
        }
    }
}