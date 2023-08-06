package readwritelock;

/**
 * @author 邱星晨
 */
class WriteLock implements Lock {
    private final ReadWriteLockImpl readWriteLock;
    public WriteLock(ReadWriteLockImpl lock) {
        readWriteLock=lock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMUTEX()){
            try{
                //等待锁的数字加1
                readWriteLock.waitsIncrease();
                while (readWriteLock.getWritingWrites()>0||readWriteLock.getReadingReaders()>0){
                    readWriteLock.getMUTEX().wait();
                }
            }finally {
                readWriteLock.waitsDecrease();
            }
            readWriteLock.writesIncrease();
        }
    }

    @Override
    public void unLock() {
        synchronized (readWriteLock.getMUTEX()){
            readWriteLock.writesDecrease();
            readWriteLock.changePref(false);
            // 唤醒等待的线程
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
