package threadcontext;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @author 邱星晨
 */
public class ThreadContext {
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal=new ThreadLocal<>();
        IntStream.range(0,10).forEach(i->new Thread(()->{
            threadLocal.set(i);
            System.out.println(currentThread()+" set i "+threadLocal.get());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread()+" get i "+threadLocal.get());
        }).start());
    }
}

class TestOne{
    public static void main(String[] args) {
        ThreadLocal<Object> threadLocal=new ThreadLocal<>(){
            @Override
            protected Object initialValue() {
                return new Object();
            }
        };
        IntStream.range(0,10).forEach(i->new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread()+" get i "+threadLocal.get().hashCode());
        }).start());
    }
}