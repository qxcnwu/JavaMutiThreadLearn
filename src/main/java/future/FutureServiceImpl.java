package future;

import ThreadPool.BasicThreadPool;
import ThreadPool.ThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 提交任务的时候创建一个新线程处理此任务，达到任务异步执行的状态
 * @author 邱星晨
 */
public class FutureServiceImpl<T, R> extends FutureTask<String> implements FutureService<T, R> {
    private final static String FUTURE_THREAD_PREFIX="FUTURE-";
    private final AtomicInteger nextCounter=new AtomicInteger(0);
    private static final ThreadPool THREAD_POOL =new BasicThreadPool(2,6,4,1000);


    private String getNextName(){
        return FUTURE_THREAD_PREFIX+nextCounter.getAndIncrement();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> futureTask=new FutureTask<>();
        THREAD_POOL.execute(()->{
            runnable.run();
            futureTask.finish(null);
        });
        return futureTask;
    }

    @Override
    public Future<R> submit(Task<T, R> task, T input) {
        final FutureTask<R> futureTask=new FutureTask<>();
        THREAD_POOL.execute(()->{
            R result= task.get(input);
            futureTask.finish(result);
        });
        return futureTask;
    }

    @Override
    public Future<R> submit(Task<T, R> task, T input, Callback<R> callback) {
        final FutureTask<R> futureTask=new FutureTask<>();
        THREAD_POOL.execute(()->{
            R result= task.get(input);
            futureTask.finish(result);
            if(null!=callback){
                callback.call(result);
            }
        });
        return futureTask;
    }

    @Override
    public ThreadPool getThreadPool() {
        return THREAD_POOL;
    }

}
