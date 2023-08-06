import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThreading extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.getName()+i);
        }
    }
}

class Tickets extends Thread{
    static Integer num=100;
    static HashSet<Integer> set=new HashSet<>();
    public Tickets(String name){
        super.setName(name);
    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (num >= 0) {
                if (set.contains(num)) {
                    System.out.println("冲突发生"+num);
                }else{
                    set.add(num);
                }
                num--;
            }
        }
    }
}

class ThreadImp implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("   "+i);
        }
    }
}

class TicketsImp implements Runnable{
    int tickets=10;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(tickets>=1){
                System.out.println(Thread.currentThread().getName()+":"+tickets--);
            }
        }
    }
}

class ThreadCal implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        return new Random().nextInt(10);
    }
}

/**
 *   线程生命周期
 *  线程阶段
 *  --thread-->新生阶段--start-->就绪状态--run-->执行方法---->消亡状态(stop弃用)
 *  阻塞状态--wait--> 就绪状态
 *  start 启动当前线程
 *  currentThread
 *  设置优先级
 *  同优先级 先到先得 时间跳
 *  优先级别高 调度概率高
 *  1-10 5正常
 *  伴随线程 主线程死了 版伴随线程借宿
**/
class ThreadPro extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }
}

class ThreadPro2 extends Thread{

    @Override
    public void run() {
        for (int i = 10; i < 100; i++) {
            System.out.println(i);
        }
    }
}

/**
 * 线程安全 加锁  在具有安全隐患的代码部分
 */
class ThreadSafe implements Runnable{

    Integer num=10000;
    HashSet<Integer> set=new HashSet<>();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (this){
                if(num>=1){
                    if(set.contains(num)){
                        System.out.println("发生冲突"+num);
                    }else
                    {
                        set.add(num);
                    }
                    num--;
                }
            }
        }
    }
}

//注意多个线程用同一把锁
class ThreadSafe2 extends Thread{

    static Integer num=10000;
    static HashSet<Integer> set=new HashSet<>();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (ThreadSafe2.class){
                if(num>=1){
                    if(set.contains(num)){
                        System.out.println("发生冲突"+num);
                    }else
                    {
                        set.add(num);
                    }
                    num--;
                }
            }
        }
    }
}

class ThreadSafe3 implements Runnable{

    static Integer num=10000;
    static HashSet<Integer> set=new HashSet<>();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
           main();
        }
    }

    public synchronized void main(){
        if(num>=1){
            if(set.contains(num)){
                System.out.println("发生冲突"+num);
            }else
            {
                set.add(num);
            }
            num--;
        }
    }
}

class ThreadSafe4 extends Thread{

    static Integer num=10000;
    static HashSet<Integer> set=new HashSet<>();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            main();
        }
    }

    public static synchronized void main(){
        if(num>=1){
            if(set.contains(num)){
                System.out.println("发生冲突"+num);
            }else
            {
                set.add(num);
            }
            num--;
        }
    }
}


class ThreadSafe5 implements Runnable{

    static Integer num=10000;
    static HashSet<Integer> set=new HashSet<>();
    Lock lock=new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            lock.lock();
            try{
                main();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }
    }

    public void main(){
        if(num>=1){
            if(set.contains(num)){
                System.out.println("发生冲突"+num);
            }else
            {
                set.add(num);
            }
            num--;
        }
    }
}

/**
 * 商品类
 *
 */
class Product{
    private String brand;
    private String name;
    public boolean isP=true;
    Lock lock=new ReentrantLock();

    Condition pC=lock.newCondition();
    Condition cC=lock.newCondition();

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void produce(int i){
        lock.lock();
        try{
            if(!this.isP) {
                try {
                    pC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.setBrand(String.valueOf(i));
            this.setName(String.valueOf(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("生产"+this);
            this.isP=!this.isP;
            cC.signal();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void cust(){
        lock.lock();
        try{
            if(this.isP) {
                try {
                    cC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.isP=!this.isP;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费"+this);
            pC.signal();
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}

/**
 * 线程之间通讯
 */
class ProducerThread extends  Thread{
    private Product p;
    Lock lock=new ReentrantLock();

    public ProducerThread(Product o){
        p=o;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            p.produce(i);
        }
    }
}

class CustomerThread extends Thread{
    private Product p;
    Lock lock=new ReentrantLock();

    public CustomerThread(Product o){
        p=o;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            p.cust();
        }
    }
}