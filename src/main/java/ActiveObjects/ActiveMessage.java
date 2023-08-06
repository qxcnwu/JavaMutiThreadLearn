package ActiveObjects;

import future.Future;
import future.FutureTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author qxc
 * @version 1.0
 * @date 2023 2023/6/19 16:21
 * @see ActiveObjects
 */
class ActiveMessage {
    /**
     * 接口的参数
     */
    private final Object[] objects;

    /**
     * 接口的方法
     */
    private final Method method;

    /**
     * 执行方法
     */
    private final FutureTask<Object> future;

    /**
     * 服务
     */
    private final Object service;

    public ActiveMessage(Object[] objects, Method method, FutureTask<Object> future, Object service) {
        this.objects = objects;
        this.method = method;
        this.future = future;
        this.service = service;
    }

    ActiveMessage(Builder builder) {
        this.objects = builder.objects;
        this.method = builder.method;
        this.future = builder.future;
        this.service = builder.service;
    }

    public void execute() {
        try {
            final Object result = method.invoke(service,objects);
            if (future != null) {
                //如果是有返回值的接口方法，则需要通过 get 方法获得最终的结果
                Future<?> realFuture = (Future<?>) result;
                Object realResult = realFuture.get();
                //将结果交给 ActiveFuture，接口方法的线程会得到返回
                future.finish(realResult);
            }
        } catch (IllegalAccessException | InvocationTargetException |InterruptedException e){
            if (future != null) {
                future.finish(null);
            }
        }
    }
}

class Builder {

    Object[] objects;

    Method method;

    FutureTask<Object> future;

    Object service;


    public Builder useMethod(Method method) {
        this.method = method;
        return this;
    }

    public Builder returnFuture(FutureTask<Object> future) {
        this.future = future;
        return this;
    }


    public Builder withObjects(Object[] objects) {
        this.objects = objects;
        return this;
    }


    public Builder forService(Object service) {
        this.service = service;
        return this;
    }

    public ActiveMessage build() {
        return new ActiveMessage(this);
    }
}

