package EventDriven;

import java.util.HashMap;

/**
 * @author 邱星晨
 */
public class EventDispatcher implements DynamicRouter<Message>{

    private final HashMap<Class<? extends Message>,Channel> map;

    public EventDispatcher() {
        this.map = new HashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Message> messageType, Channel<? extends Message> channel) {
        this.map.put(messageType,channel);
    }

    @Override
    public void dispatch(Message message) {
        if (this.map.containsKey(message.getType())) {
            map.get(message.getType()).dispatch(message);
        }else{
            throw new RuntimeException("no such message has been register"+message);
        }
    }
}
