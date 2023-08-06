package ThreadPool;

/**
 * 线程池接口
 */
public interface ThreadPool {
    /**
     * 向线程池提交任务
     * @param runnable
     */
    void execute(Runnable runnable);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 获取线程池初始大小
     * @return
     */
    Integer getInitSize();

    /**
     * 获取线程池的最大数量
     * @return
     */
    Integer getMaxSize();

    /**
     * 获取当前线程池核心线程数量大小
     * @return
     */
    Integer getCoreSize();

    /**
     * 获取线程池中用于缓存任务队列的大小
     * @return
     */
    Integer getQueueSize();

    /**
     * 获取线程池中活动线程的数量
     * @return
     */
    Integer getActiveCount();

    /**
     * 线程池是否关闭
     * @return
     */
    boolean isShutDown();
}
