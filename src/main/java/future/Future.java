package future;

/**
 * @author 邱星晨
 */
public interface Future<T> {
    /**
     * 返回计算后的结果
     * @return
     * @throws InterruptedException
     */
    T get() throws InterruptedException;

    /**
     * 判断是否完成
     * @return
     */
    boolean isDone();
}
