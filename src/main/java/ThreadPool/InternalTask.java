package ThreadPool;

/**
 * 执行线程池里面的方法
 */
public class InternalTask implements Runnable{
    private final RunnableQueue runnableQueue;
    private volatile boolean running=true;

    public InternalTask(RunnableQueue runnableQueue){
        this.runnableQueue=runnableQueue;
    }

    @Override
    public void run() {
        while (running&&!Thread.currentThread().isInterrupted()){
            Runnable task;
            try {
                task = runnableQueue.take();
                task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        this.running=false;
    }
}
