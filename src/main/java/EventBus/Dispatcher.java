package EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author 邱星晨
 */
public class Dispatcher {

    private final Executor executorService;
    private final EventExceptionHandler eventExceptionHandler;
    public static final Executor SEQ_EXECTOR_SERVICE = SeqExecutorService.INSTANCE;
    public static final Executor PRE_THREAD_EXECUTOR_SERVICE=PreThreadExecutorService.INSTANCE;

    private Dispatcher(Executor executorService,EventExceptionHandler eventExceptionHandler){
        this.executorService=executorService;
        this.eventExceptionHandler=eventExceptionHandler;
    }

    public static Dispatcher newDispatcher(EventExceptionHandler exceptionHandler, Executor executor) {
        return new Dispatcher(executor,exceptionHandler);
    }

    public void dispatch(Bus bus, Registry registry, Object event, String topic) {
        ConcurrentLinkedQueue<Subscriber> subscribers=registry.scanSubscriber(topic);
        if(null==subscribers){
            if(eventExceptionHandler!=null){
                eventExceptionHandler.handle(new IllegalArgumentException(topic+" not bind"),new BaseEventContext(bus.getBusName(),null,event));
            }
            return;
        }
        subscribers.stream()
                .filter(subscriber -> !subscriber.isDisable())
                .filter(subscriber -> {
                    Method m= subscriber.getSubMethod();
                    Class<?> clazz=m.getParameterTypes()[0];
                    return (clazz.isAssignableFrom(event.getClass()));
                }).forEach(subscriber -> realInvokeSubscribe(subscriber,event,bus));
    }

    private void realInvokeSubscribe(Subscriber subscriber, Object event, Bus bus) {
        final Method subMethod = subscriber.getSubMethod();
        final Object subscribObject = subscriber.getSubscribObject();
        executorService.execute(()->{
            try {
                subMethod.invoke(subscribObject,event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                if (null!=eventExceptionHandler) {
                    eventExceptionHandler.handle(e,new BaseEventContext(bus.getBusName(),subscriber,event));
                }
            }
        });
    }

    public void close() {
        if (executorService instanceof ExecutorService) {
            ((ExecutorService) executorService).shutdown();
        }
    }

    public static Dispatcher seqDispatcher(EventExceptionHandler exceptionHandler){
        return new Dispatcher(SEQ_EXECTOR_SERVICE,exceptionHandler);
    }

    public static Dispatcher preDispatcher(EventExceptionHandler exceptionHandler){
        return new Dispatcher(PRE_THREAD_EXECUTOR_SERVICE,exceptionHandler);
    }

    /**
     * 顺序执行
     */
    private static class SeqExecutorService implements Executor{
        public final static SeqExecutorService INSTANCE=new SeqExecutorService();
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PreThreadExecutorService implements Executor{
        public final static PreThreadExecutorService INSTANCE=new PreThreadExecutorService();
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class BaseEventContext implements EventContext {
        private final String busName;
        private final Subscriber subscriber;
        private final Object object;

        private BaseEventContext(String busName, Subscriber subscriber, Object object) {
            this.busName = busName;
            this.subscriber = subscriber;
            this.object = object;
        }


        @Override
        public String getRource() {
            return this.busName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber!=null?subscriber.getSubscribObject():null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber!=null?subscriber.getSubMethod():null;
        }

        @Override
        public Object getEvent() {
            return this.object;
        }
    }
}
