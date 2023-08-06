package EventBus;

import java.util.Arrays;

public interface Bus {
    /**
     * 某个对象注册到Bus上使其成为Subscriber
     *
     * @param subscriber
     */
    void register(Object subscriber);

    /**
     * 取消注册不会接受消息
     *
     * @param subscriber
     */
    void unregister(Object subscriber);

    /**
     * 提交时间到默认的topic
     *
     * @param event
     */
    void post(Object event);

    /**
     * 提交事件到指定的event
     *
     * @param event
     * @param topic
     */
    void post(Object event, String topic);

    /**
     * 关闭bus
     */
    void close();

    /**
     * 获取bus名称
     *
     * @return
     */
    String getBusName();
}

