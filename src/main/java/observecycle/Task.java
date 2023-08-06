package observecycle;

/**
 * @author 邱星晨
 */
public interface Task<T> {
    /**
     * 任务执行接口，允许有返回值
     * @return
     */
    T call();
}
