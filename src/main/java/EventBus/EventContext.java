package EventBus;

import java.lang.reflect.Method;

/**
 * @author 邱星晨
 */
public interface EventContext {
    /**
     * @return
     */
    String getRource();

    /**
     * @return
     */
    Object getSubscriber();

    /**
     * @return
     */
    Method getSubscribe();

    /**
     * @return
     */
    Object getEvent();
}
