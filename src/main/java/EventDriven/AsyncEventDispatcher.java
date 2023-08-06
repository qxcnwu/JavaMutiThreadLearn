package EventDriven;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 邱星晨
 */
public class AsyncEventDispatcher implements DynamicRouter<Event>{
    private final Map<Class<? extends Message>,AsyncChannel> map;

    public AsyncEventDispatcher() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Event> messageType, Channel<? extends Event> channel) {
        final AsyncChannel asyncChannel = channel instanceof AsyncChannel ? ((AsyncChannel) channel) : null;
        if (null!=asyncChannel) {
            map.put(messageType,asyncChannel);
        }
    }

    @Override
    public void dispatch(Event message) {
        if (map.get(message.getType())!=null) {
            map.get(message.getType()).dispatch(message);
        }
    }

    public void shutdown(){
        map.values().forEach(AsyncChannel::stop);
    }
}
