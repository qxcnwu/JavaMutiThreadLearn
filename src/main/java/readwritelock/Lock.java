package readwritelock;

/**
 * @author 邱星晨
 */
public interface Lock {
    /**
     * 上锁 未获得锁的线程被堵塞
     * @throws InterruptedException
     */
    void lock() throws InterruptedException;

    /**
     * 解锁
     */
    void unLock();
}
