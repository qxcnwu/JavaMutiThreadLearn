package observecycle;

/**
 * @author 邱星晨
 */
public interface Observable {
    /**
     * 任务周期枚举类型
     */
    enum Cycle{
        //开始，运行中，结束，错误
        START,RUNNING,DONE,ERROR
    }

    /**
     * 获取当前的生命周期状态
     * @return
     */
    Cycle getCycle();

    /**
     * 开始
     */
    void start();

    /**
     * 打断
     */
    void interrupt();
}
