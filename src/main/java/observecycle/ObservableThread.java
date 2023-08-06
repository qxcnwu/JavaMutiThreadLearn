package observecycle;

/**
 * @author 邱星晨
 */
public class ObservableThread<T> extends Thread implements Observable {

    private final TaskLifeCycle<T> lifeCycle;
    private final Task<T> task;
    private Cycle cycle;

    public ObservableThread(Task<T> task) {
        this(new EmptyLifeCycle<T>(), task);
    }

    public ObservableThread(TaskLifeCycle<T> tEmptyLifeCycle, Task<T> task) {
        super();
        if (task == null) {
            throw new NullPointerException("task can not be null");
        }
        this.lifeCycle = tEmptyLifeCycle;
        this.task = task;
    }

    @Override
    public final void run() {
        //执行开始thread.start
        this.update(Cycle.START, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (lifeCycle == null) {
            return;
        }
        try {
            switch (cycle) {
                case START:
                    this.lifeCycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifeCycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifeCycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifeCycle.onError(currentThread(), e);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.DONE) {
                throw ex;
            }
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
