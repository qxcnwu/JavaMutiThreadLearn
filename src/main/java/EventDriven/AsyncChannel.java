package EventDriven;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 邱星晨
 */
public abstract class AsyncChannel implements Channel<Event>{
    private final ExecutorService executorService;

    protected AsyncChannel() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
    }

    @Override
    public void dispatch(Event message) {
        executorService.submit(()->{
            this.handle(message);
        });
    }

    protected abstract void handle(Event event);

    public void stop(){
        if (null!=executorService&&executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
