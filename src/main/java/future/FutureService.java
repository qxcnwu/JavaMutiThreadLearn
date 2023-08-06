package future;


import ThreadPool.ThreadPool;

/**
 * @author 邱星晨
 */
public interface FutureService<IN,OUT> {
    /**
     * 提交不需要返回值的任务
     * @param runnable
     * @return
     */
    Future<?> submit(Runnable runnable);

    /**
     * 有返回值的任务
     * @param task
     * @param input
     * @return
     */
    Future<OUT> submit(Task<IN,OUT> task, IN input);

    /**
     * 添加回调机制
     * @param task
     * @param input
     * @param callback
     * @return
     */
    Future<OUT> submit(Task<IN,OUT> task, IN input,Callback<OUT> callback);

    /**
     * 返回线程池对象获取线程池状态
     * @return
     */
    ThreadPool getThreadPool();

    /**
     * 静态类生成Future服务
     * @param <T>
     * @param <R>
     * @return
     */
    static <T,R> FutureService<T,R> newService(){
        return new FutureServiceImpl<T, R>();
    }
}
