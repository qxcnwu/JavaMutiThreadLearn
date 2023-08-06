package EventBus;


import java.util.concurrent.Executor;

/**
 * @author 邱星晨
 */
public class EventBus implements Bus{
    /**
     * 维护Subscriber的注册表
     */
    private final Registry registry=new Registry();
    private String busName;
    private final static String DEFAULT_BUS_NAME = "default";
    private final static String DEFAULT_TOPIC = "default-topic";
    /**
     * 分发广播消息的类
     */
    private final Dispatcher dispatcher;

    public EventBus(){
        this(DEFAULT_BUS_NAME,null,Dispatcher.SEQ_EXECTOR_SERVICE);
    }

    public EventBus(String busName){
        this(busName,null,Dispatcher.SEQ_EXECTOR_SERVICE);
    }

    public EventBus(String busName, EventExceptionHandler exceptionHandler, Executor executor){
        this.busName=busName;
        this.dispatcher=Dispatcher.newDispatcher(exceptionHandler,executor);
    }

    public EventBus(EventExceptionHandler exceptionHandler){
        this(DEFAULT_BUS_NAME,exceptionHandler,Dispatcher.SEQ_EXECTOR_SERVICE);
    }


    /**
     * 将注册动作直接委托给Registry
     * @param subscriber
     */
    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    /**
     * 结束注册同样交给委托Registry
     * @param subscriber
     */
    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    /**
     * 默认提交
     * @param event
     */
    @Override
    public void post(Object event) {
        this.post(event,DEFAULT_TOPIC);
    }

    /**
     * 通过dispatcher提交到事件
     * @param event
     * @param topic
     */
    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this,registry,event,topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
