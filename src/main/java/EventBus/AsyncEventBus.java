package EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步EventBus
 * @author 邱星晨
 */
public class AsyncEventBus extends EventBus{
    AsyncEventBus(String busName, EventExceptionHandler eventExceptionHandler, Executor executor){
        super(busName,eventExceptionHandler,executor);
    }

    public AsyncEventBus(String busName, ThreadPoolExecutor threadPoolExecutor){
        this(busName,null,threadPoolExecutor);
    }

    public AsyncEventBus(ThreadPoolExecutor threadPoolExecutor){
        this("default-async",threadPoolExecutor);
    }

    AsyncEventBus(EventExceptionHandler eventExceptionHandler, Executor executor){
        this("default-async",eventExceptionHandler,executor);
    }
}
