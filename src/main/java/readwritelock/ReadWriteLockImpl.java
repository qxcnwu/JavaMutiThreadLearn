package readwritelock;

/**
 * @author 邱星晨
 */
class ReadWriteLockImpl implements ReadWriteLock {
    /**
     * 定义对象锁
     */
    private final Object MUTEX=new Object();
    /**
     * 写入线程数量
     */
    private int writingWrites=0;
    /**
     * 等待写入锁的线程数量
     */
    private int writingWaits=0;
    /**
     * 读取的线程数量
     */
    private int readingReaders=0;
    /**
     * 是否是写偏向锁
     */
    private boolean preferWrite;
    
    public ReadWriteLockImpl() {
        this(true);
    }
    
    public ReadWriteLockImpl(boolean preferWrite) {
        this.preferWrite=preferWrite;
    }
    @Override
    public Lock readLock() {
        return new ReadLock(this);
    }

    @Override
    public Lock writeLock() {
        return new WriteLock(this);
    }

    @Override
    public int getWritingWrites() {
        return writingWrites;
    }

    @Override
    public int getWaitingWrites() {
        return writingWaits;
    }

    @Override
    public int getReadingReaders() {
        return readingReaders;
    }
    
    public void writesIncrease(){
        this.writingWrites++;
    }
    
    public void waitsIncrease(){
        this.writingWaits++;
    }
    
    public void readsIncrease(){
        this.readingReaders++;
    }

    public void writesDecrease(){
        this.writingWrites--;
    }

    public void waitsDecrease(){
        this.writingWaits--;
    }

    public void readsDecrease(){
        this.readingReaders--;
    }

    /**
     * 是否是写偏向锁
     * @return
     */
    public boolean isWritePerf(){
        return preferWrite;
    }

    /**
     * 获取锁对象
     * @return
     */
    Object getMUTEX(){
        return MUTEX;
    }

    /**
     * 更改偏向锁的设置
     * @param preferWrite
     */
    void changePref(boolean preferWrite){
        this.preferWrite=preferWrite;
    }
}
