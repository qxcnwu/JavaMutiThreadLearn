package ActiveObjects;

public class MethodDaemonThread extends Thread{
    private final MethodMessageQueue queue;

    public MethodDaemonThread(MethodMessageQueue queue) {
        super("MethodDaemonThread");
        this.queue = queue;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true){
            // 执行待加工产品的加工方法
            MethodMessage methodMessage = this.queue.take();
            methodMessage.execute();
        }
    }
}