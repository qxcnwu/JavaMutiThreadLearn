package EventDriven;

/**
 * @author 邱星晨
 */
public interface Channel<E extends Message> {
    /**
     * 获取循环分配的消息
     * @param message
     */
    void dispatch(E message);
}
