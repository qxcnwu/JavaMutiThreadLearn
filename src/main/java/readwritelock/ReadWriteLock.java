package readwritelock;

/**
 * @author 邱星晨
 */
public interface ReadWriteLock {
    /**
     * 生成reader锁
     * @return
     */
    Lock readLock();

    /**
     * 创建write锁
     * @return
     */
    Lock writeLock();

    /**
     * 当前有多少线程在写入
     * @return
     */
    int getWritingWrites();

    /**
     * 当前有多少线程在等待写入
     * @return
     */
    int getWaitingWrites();

    /**
     * 当前多少线程在读取
     * @return
     */
    int getReadingReaders();

    /**
     * 创建readWriteLock
     * @return
     */
    static ReadWriteLock readWriteLock() {
        return new ReadWriteLockImpl();
    }

    /**
     * 工厂方法创建 readWriteLock 并且传入preferWrite
     * @param preferWrite
     * @return
     */
    static ReadWriteLock readWriteLock(boolean preferWrite){
        return new ReadWriteLockImpl(preferWrite);
    }
}
