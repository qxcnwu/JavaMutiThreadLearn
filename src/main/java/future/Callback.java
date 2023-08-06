package future;

/**
 * @author 邱星晨
 */
public interface Callback<T> {
    /**
     * 任务完成后调用此方法 T为任务的执行结果
     * @param t
     */
    public void call(T t);
}
