package BooleanLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;

public class BooleanLock implements Lock{

    //当前拥有锁的线程
    private Thread currentThread;
    //当前锁没有任何线程获取或者释放
    private boolean locked=false;
    //哪些线程在获取当前线程时进入堵塞状态
    private final List<Thread> blockedList=new ArrayList<>();

    @Override
    public void lock() throws InterruptedException {
        synchronized (this){
            while(locked){
                final Thread tempThread=Thread.currentThread();
                try{
                    if(!blockedList.contains(tempThread)){
                        blockedList.add(tempThread);
                    }
                    this.wait();
                }catch (InterruptedException e){
                    blockedList.remove(tempThread);
                    throw e;
                }
            }
            blockedList.remove(Thread.currentThread());
            this.locked=true;
            this.currentThread=Thread.currentThread();
        }
    }

    @Override
    public void lock(long milliseconds) throws InterruptedException, TimeoutException {
        if(milliseconds<0){
            lock();
        }
        else{
            long remainingMills=milliseconds;
            long endMillSec=currentTimeMillis()+remainingMills;
            while(locked){
                if (remainingMills<=0) {
                    throw new TimeoutException("当前线程等待超时"+milliseconds+"ms"+Thread.currentThread().getName());
                }
                if(!blockedList.contains(Thread.currentThread())){
                    blockedList.add(Thread.currentThread());
                }
                this.wait(remainingMills);
                remainingMills=endMillSec-currentTimeMillis();
            }
            blockedList.remove(Thread.currentThread());
            this.locked=true;
            this.currentThread=Thread.currentThread();
        }
    }

    @Override
    public void tryLock() {
        if(!locked){
            this.locked=true;
            this.currentThread=Thread.currentThread();
        }
    }

    /**
     * 只有加锁的线程才有资格解锁
     */
    @Override
    public void unlock() {
        synchronized (this){
            if(this.currentThread==Thread.currentThread()){
                this.locked=false;
                Optional.of(currentThread.getName()+" release the lock").ifPresent(System.out::println);
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThread() {
        return Collections.unmodifiableList(blockedList);
    }
}

