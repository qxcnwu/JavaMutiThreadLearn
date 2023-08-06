package future;

/**
 * @author 邱星晨
 */
public interface Task<IN, OUT> {
    /**
     * 给定参数返回计算结果
     * @param input
     * @return
     */
    OUT get(IN input);
}
