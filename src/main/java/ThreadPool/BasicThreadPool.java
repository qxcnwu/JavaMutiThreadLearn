package ThreadPool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池初始化
 */
public class BasicThreadPool extends Thread implements ThreadPool{
    //初始化大小
    private final int initSize;
    //最大大小
    private final int maxSize;
    //核心线程大小
    private final int coreSize;
    //当前活跃线程大小
    private int activeSize;
    //线程工厂
    private final ThreadFactory threadFactory;
    //任务队列
    private final RunnableQueue runnableQueue;
    //线程池是否被shutdown
    private volatile boolean shutdown=false;
    //工作线程队列
    private final Queue<ThreadTask> threadQueue=new ArrayDeque<>();
    //默认线程满策略
    private final static DenyPolice DENY_POLICE=new DenyPolice.DiscardDenyPolice();
    //默认线程工厂
    private final static ThreadFactory defaultThreadFactory=new DefaultThreadFactory();

    private final TimeUnit timeUnit;
    //更新时间
    private final long keepAlive;

    public BasicThreadPool(int initSize,int maxSize,int coreSize,int queueSize){
        this(initSize,maxSize,coreSize,queueSize,defaultThreadFactory,DENY_POLICE,TimeUnit.SECONDS,5);
    }

    public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize, ThreadFactory threadFactory,
                           DenyPolice denyPolice, TimeUnit timeUnit,long keepAlive){
        this.initSize=initSize;
        this.maxSize=maxSize;
        this.coreSize=coreSize;
        this.threadFactory=threadFactory;
        this.runnableQueue=new LinkedRunnableQueue(queueSize,denyPolice,this);
        this.timeUnit=timeUnit;
        this.keepAlive=keepAlive;
        this.init();
    }

    private void init(){
        start();
        for (int i = 0; i <initSize; i++) {
            newThread();
        }
    }

    private void newThread(){
        //创建线程启动任务
        InternalTask internalTask=new InternalTask(runnableQueue);
        Thread thread=this.threadFactory.createThread(internalTask);
        ThreadTask threadTask=new ThreadTask(thread,internalTask);
        threadQueue.offer(threadTask);
        this.activeSize++;
        thread.start();
    }

    /**
     * 关闭一个线程
     * @return
     */
    private void removeThread(){
        ThreadTask threadTask=threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeSize--;
    }

    /**
     * 维护线程池操作
     */
    @Override
    public void run() {
        while(!shutdown&&!isInterrupted()){
            try {
                timeUnit.sleep(keepAlive);
            } catch (InterruptedException e) {
                shutdown=true;
                break;
            }
            synchronized (this){
                if(shutdown){
                    break;
                }
                //当前队列尚有任务没有处理，并且 则继续扩容
                if(runnableQueue.size()>0&&activeSize<coreSize){
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    //不让线程扩容直接达到maxsize
                    continue;
                }
                //如果队列中有任务没有处理，并且 则继续扩容到maxsize
                if(runnableQueue.size()>0&&activeSize<maxSize){
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                }
                //如果队列中没有任务则回收到coreSize
                if(runnableQueue.size()==0&&activeSize>coreSize){
                    for (int i = coreSize; i < activeSize; i++) {
                        removeThread();
                    }
                }
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if(this.shutdown){
            throw new IllegalStateException("This thread pool is shutdown");
        }
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void shutdown() {
        synchronized (this){
            if(shutdown) {
                return;
            }
            shutdown=true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
        }
    }

    @Override
    public Integer getInitSize() {
        if(this.shutdown){
            throw new IllegalStateException("This thread pool is shutdown");
        }
        return initSize;
    }

    @Override
    public Integer getMaxSize() {
        if(this.shutdown){
            throw new IllegalStateException("This thread pool is shutdown");
        }
        return maxSize;
    }

    @Override
    public Integer getCoreSize() {
        if(this.shutdown){
            throw new IllegalStateException("This thread pool is shutdown");
        }
        return coreSize;
    }

    @Override
    public Integer getQueueSize() {
        if(this.shutdown){
            throw new IllegalStateException("This thread pool is shutdown");
        }
        return runnableQueue.size();
    }

    @Override
    public Integer getActiveCount() {
        if(this.shutdown){
            throw new IllegalStateException("This thread pool is shutdown");
        }
        return activeSize;
    }

    @Override
    public boolean isShutDown() {
        return shutdown;
    }

    /**
     * 默认的线程工厂
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger GROUP_COUNTER=new AtomicInteger(1);
        private static final ThreadGroup group=new ThreadGroup("ThreadPool-"+GROUP_COUNTER.getAndIncrement());
        private static final AtomicInteger COUNTER=new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group,runnable,"thread-pool-"+COUNTER.getAndIncrement());
        }
    }

    /**
     * InternalTask 和 thread 的组合
     */
    private static class ThreadTask {
        public ThreadTask(Thread thread,InternalTask internalTask){
            this.thread=thread;
            this.internalTask=internalTask;
        }
        Thread thread;
        InternalTask internalTask;
    }
}