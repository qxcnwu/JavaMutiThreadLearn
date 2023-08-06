import ClassLoaders.ClassLoaderPlus;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * 单例设计模式
 */
public class Singleton {
    private static CountDownLatch cd =new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        /*
          TestHungry();
          TestLazy();
          TestDoubleCheck();
          TestHolder();
          TestEnum();
         */
        TestLEnum();
    }

    public static void TestHungry() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            Hungary hungary= Hungary.getInstance();
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    hungary.Inc();
                }
                cd.countDown();
            }).start();
        }
        cd.await();
        Hungary.getInstance().getNum();
    }

    public static void TestLazy() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Lazy lazy= Lazy.getInstance();
            System.out.println(lazy.hashCode());
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    lazy.Inc();
                }
                cd.countDown();
            }).start();
        }
        cd.await();
        Lazy.getInstance().getNum();
    }

    public static void TestDoubleCheck() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            DoubleCheck lazy= DoubleCheck.getInstance();
            if(lazy.socket==null){
                System.out.println(lazy.cls);
            }
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    lazy.Inc();
                }
                cd.countDown();
            }).start();
        }
        cd.await();
        DoubleCheck.getInstance().getNum();
    }

    public static void TestHolder() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Holder lazy= Holder.getInstance();
            System.out.println(lazy.hashCode());
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    lazy.Inc();
                }
                cd.countDown();
            }).start();
        }
        cd.await();
        Holder.getInstance().getNum();
    }

    public static void TestEnum() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Enum lazy= Enum.getInstance();
            System.out.println(lazy.hashCode());
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    lazy.Inc();
                }
                cd.countDown();
            }).start();
        }
        cd.await();
        Enum.getInstance().getNum();
    }

    public static void TestLEnum() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            LEnum lazy= LEnum.getInstance();
            System.out.println(lazy.hashCode());
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    lazy.Inc();
                }
                cd.countDown();
            }).start();
        }
        cd.await();
        LEnum.getInstance().getNum();
    }
}

/**
 * 饿汉模式 避免提前创建
 */
final class Hungary{
    private Integer num=0;
    private static final Hungary instance=new Hungary();
    private Hungary(){}
    public static Hungary getInstance(){
        return instance;
    }
    public synchronized void Inc(){
        num++;
    }
    public void getNum(){
        System.out.println(num);
    }
}

/**
 * 懒汉模式
 */
final class Lazy{
    private Integer num=0;
    private static Lazy instance=null;
    private Lazy(){}

    public synchronized static Lazy getInstance(){
        if(instance!=null){
            return instance;
        }else{
            instance=new Lazy();
            return instance;
        }
    }

    public synchronized void Inc(){
        num++;
    }

    public void getNum(){
        System.out.println(num);
    }
}

/**
 * 两次检查
 */
final class DoubleCheck{
    ClassLoader cls;
    Socket socket;
    private Integer num=0;
    private volatile static DoubleCheck instance=null;

    private DoubleCheck(){
        this.cls=new ClassLoaderPlus();
        this.socket=new Socket();
    }

    public static DoubleCheck getInstance(){
        if(instance==null){
            synchronized (DoubleCheck.class){
                if (instance==null) {
                    instance=new DoubleCheck();
                }
            }
        }
        return instance;
    }
    public synchronized void Inc(){
        num++;
    }
    public void getNum(){
        System.out.println(num);
    }
}

/**
 * Holder模式
 */
final class Holder{
    private Holder(){}
    private Integer num=0;
    private static class holder {
        private final static Holder instance=new Holder();
    }

    public static Holder getInstance(){
        return holder.instance;
    }

    public synchronized void Inc(){
        num++;
    }

    public void getNum(){
        System.out.println(num);
    }
}

/**
 * 枚举方式
 */
enum Enum{
    INSTANCE;
    private Integer num=0;
    Enum(){
        System.out.println("init");
    }
    public static void Method(){}

    public static Enum getInstance(){
        return INSTANCE;
    }
    public synchronized void Inc(){
        num++;
    }
    public void getNum(){
        System.out.println(num);
    }
}

/**
 * 懒汉枚举式
 */
final class LEnum{
    private Integer num=0;
    private LEnum(){

    }

    public synchronized void Inc() {
        num++;
    }

    public void getNum() {
        System.out.println(num);
    }

    private enum EnumHolder{
        INSTANCE;
        private LEnum instance;
        EnumHolder() {
            this.instance=new LEnum();
        }
        private LEnum getInstance(){
            return instance;
        }
    }

    public static LEnum getInstance(){
        return EnumHolder.INSTANCE.getInstance();
    }
}