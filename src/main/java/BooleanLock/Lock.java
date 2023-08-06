package BooleanLock;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface Lock {
    /**
     * 永远阻塞，除非获取到锁
     * @throws InterruptedException
     */
    void lock() throws InterruptedException;

    /**
     * 可以被中断外添加了超时功能
     * @param milliseconds 超时时间
     * @throws InterruptedException
     * @throws TimeoutException
     */
    void lock(long milliseconds) throws InterruptedException, TimeoutException;

    /**
     * 尝试获得锁，能获得就获得不能获得就退出
     */
    void tryLock();

    /**
     * 锁的释放
     */
    void unlock();

    /**
     * 获取当前那些线程被阻塞
     * @return 阻塞线程列表
     */
    List<Thread> getBlockedThread();
}
