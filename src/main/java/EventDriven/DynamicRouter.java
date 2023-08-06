package EventDriven;

/**
 * @author 邱星晨
 */
public interface DynamicRouter <E extends Message>{
    /**
     * 注册消息接受列表
     * @param messageType
     * @param channel
     */
    void registerChannel(Class<? extends E> messageType,Channel<? extends E> channel);

    /**
     * 分配相应的消息
     * @param message
     */
    void dispatch(E message);
}
