package EventBus;

import java.lang.reflect.Method;

/**
 * @author 邱星晨
 */
public class Subscriber{
    private final Object subscribeObject;
    private final Method subscribeMethod;
    private boolean disable=false;
    public Subscriber(Object subscribe, Method m) {
        subscribeMethod=m;
        subscribeObject=subscribe;
    }

    public Object getSubscribObject() {
        return subscribeObject;
    }

    public void setDisable(boolean b) {
        this.disable=b;
    }

    public boolean isDisable(){
        return disable;
    }

    public Method getSubMethod(){
        return subscribeMethod;
    }
}
