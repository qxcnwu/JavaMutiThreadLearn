package observecycle;

/**
 * @author 邱星晨
 */
public interface TaskLifeCycle<T> {
    /**
     * 开始
     * @param thread
     */
    void onStart(Thread thread);

    /**
     * 运行
     * @param thread
     */
    void onRunning(Thread thread);

    /**
     * 结束
     * @param thread
     * @param result
     */
    void onFinish(Thread thread,T result);

    /**
     * 错误
     * @param thread
     * @param e
     */
    void onError(Thread thread,Exception e);
}

/**
 * 生命周期接口空实现
 * @param <T>
 */
class EmptyLifeCycle<T> implements TaskLifeCycle<T>{

    @Override
    public void onStart(Thread thread) {

    }

    @Override
    public void onRunning(Thread thread) {

    }

    @Override
    public void onFinish(Thread thread, T result) {

    }

    @Override
    public void onError(Thread thread, Exception e) {

    }
}