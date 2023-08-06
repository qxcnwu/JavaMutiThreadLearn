package future;

/**
 * @author 邱星晨
 */
public class FutureTask<T> implements Future<T> {
    private T result;
    private boolean done;
    private final Object lock=new Object();

    @Override
    public T get() throws InterruptedException {
        synchronized (lock){
            //未完成陷入阻塞状态
            while(!done){
                lock.wait();
            }
            return result;
        }
    }

    public void finish(T result){
        synchronized (lock){
            if(done){
                return;
            }
            this.result=result;
            this.done=true;
            lock.notifyAll();
        }
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
