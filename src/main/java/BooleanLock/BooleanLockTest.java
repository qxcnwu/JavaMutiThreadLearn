package BooleanLock;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class BooleanLockTest {
    private final Lock lock=new BooleanLock();

    public void syncMethod(){
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            int randomInt=current().nextInt(10);
            System.out.println(currentThread()+" get the lock!!");
            TimeUnit.SECONDS.sleep(randomInt);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BooleanLockTest blt=new BooleanLockTest();
        IntStream.range(0,10).mapToObj(i->new Thread(blt::syncMethod)).forEach(Thread::start);

        new Thread(blt::syncMethod).start();
        Thread.sleep(100);
        Thread thd=new Thread(blt::syncMethod);
        thd.start();
        Thread.sleep(100);
        thd.interrupt();
    }
}
