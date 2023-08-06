package ThreadPool;

public interface RunnableQueue {
    /**
     * 添加新任务
     * @param runnable
     */
    void offer(Runnable runnable);

    /**
     * 工作线程通过take方法获取Runnable
     * @return
     */
    Runnable take() throws InterruptedException;

    /**
     * 获取任务队列中任务数量
     * @return
     */
    int size();

}
