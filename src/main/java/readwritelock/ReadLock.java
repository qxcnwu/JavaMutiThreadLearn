package readwritelock;

/**
 * @author 邱星晨
 */
class ReadLock implements Lock {
    private final ReadWriteLockImpl readWriteLock;

    public ReadLock(ReadWriteLockImpl lock) {
        readWriteLock = lock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMUTEX()) {
            while (readWriteLock.getWritingWrites() > 0 || (readWriteLock.isWritePerf())
                    && readWriteLock.getWaitingWrites() > 0) {
                readWriteLock.getMUTEX().wait();
            }
            //成功获取锁
            readWriteLock.readsIncrease();
        }
    }

    @Override
    public void unLock() {
        synchronized (readWriteLock.getMUTEX()) {
            readWriteLock.readsDecrease();
            readWriteLock.changePref(true);
            //唤醒等待的线程
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
