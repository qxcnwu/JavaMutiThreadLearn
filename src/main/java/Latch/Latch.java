package Latch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 等待计数器
 * @author 邱星晨
 */
public abstract class Latch {
    protected int limit;

    public Latch(int limit){
        this.limit=limit;
    }

    /**
     * 等待方法
     * @throws InterruptedException
     */
    public abstract void await() throws InterruptedException;

    /**
     * 含超时等待的方法
     * @param timeUnit
     * @param time
     * @throws InterruptedException
     */
    public abstract void await(TimeUnit timeUnit,long time) throws InterruptedException, TimeoutException;

    /**
     * 计数器剑法
     */
    public abstract void countDown();

    /**
     * 返回有多少个线程没有完成任务
     * @return
     */
    public abstract int getUnarrived();
}
