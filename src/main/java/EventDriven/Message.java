package EventDriven;

/**
 * @author 邱星晨
 */
public interface Message {
    /**
     * 获取相关类型
     * @return
     */
    Class<? extends Message> getType();
}
