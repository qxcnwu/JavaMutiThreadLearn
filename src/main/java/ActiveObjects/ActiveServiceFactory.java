package ActiveObjects;

import future.Future;
import future.FutureTask;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author qxc
 * @version 1.0
 * @date 2023 2023/6/19 16:23
 * @see ActiveObjects
 */
public class ActiveServiceFactory {
    private final static ActiveMessageQueue QUEUE = new ActiveMessageQueue();

    public static <T> T active(T instance) {
        return (T) Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(),
                new ActiveInvocationHandler<>(instance)
        );
    }

    public static class ActiveInvocationHandler<T> implements InvocationHandler {
        private final T instance;

        public ActiveInvocationHandler(T instance) {
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(ActiveMethod.class)) {
                this.checkMethod(method);
                Builder builder = new Builder();
                builder.useMethod(method).withObjects(args).forService(instance);
                FutureTask<Object> result = null;
                if (this.isReturnFutureType(method)) {
                    result = new FutureTask<>();
                    builder.returnFuture(result);
                }
                QUEUE.offer(builder.build());
                return result;
            } else {
                return method.invoke(instance, args);
            }
        }

        private void checkMethod(Method method) {
            if (!isReturnVoidType(method) && !isReturnFutureType(method)) {
                throw new IllegalArgumentException("the method [" + method.getName() + " return type must be void/Future");
            }
        }

        private boolean isReturnVoidType(Method method) {
            return method.getReturnType().equals(Void.TYPE);
        }

        private boolean isReturnFutureType(Method method) {
            return method.getReturnType().isAssignableFrom(Future.class);
        }
    }
}
