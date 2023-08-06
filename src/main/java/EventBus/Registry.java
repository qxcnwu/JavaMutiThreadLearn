package EventBus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 邱星晨
 */
class Registry {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subList=new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        List<Method> subMethods = getsubMethod(subscriber);
        subMethods.forEach(m->{
            tierSubscriber(subscriber,m);
        });
    }

    public void unbind(Object subscriber) {
        subList.forEach((key,queue)->{
            queue.forEach(s->{
                if(s.getSubscribObject()==subscriber){
                    s.setDisable(true);
                }
            });
        });
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic){
        return subList.get(topic);
    }

    private void tierSubscriber(Object subscriber, Method m) {
        final Subscribe subscribe=m.getDeclaredAnnotation(Subscribe.class);
        String topic= subscribe.topic();
        subList.computeIfAbsent(topic,key-> new ConcurrentLinkedQueue<>());
        subList.get(topic).add(new Subscriber(subscriber,m));
    }

    private List<Method> getsubMethod(Object subscriber) {
        final List<Method> methods=new ArrayList<>();
        Class<?> temp=subscriber.getClass();
        while(temp!=null){
            Method[] deM=temp.getDeclaredMethods();
            Arrays.stream(deM).filter(m->m.isAnnotationPresent(Subscribe.class)&&
                    m.getParameterCount()==1&&
                    m.getModifiers()== Modifier.PUBLIC).forEach(methods::add);
            temp=temp.getSuperclass();
        }
        return methods;
    }
}
