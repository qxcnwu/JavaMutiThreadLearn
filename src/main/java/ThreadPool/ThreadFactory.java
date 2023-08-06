package ThreadPool;


/**
 * 创建线程的接口，个性化定制Thread
 */
@FunctionalInterface
public interface ThreadFactory {
    /**
     * 创建线程
     * @param runnable
     * @return
     */
    Thread createThread(Runnable runnable);
}
