package ActiveObjects;

import java.util.LinkedList;

/**
 * @author qxc
 * @version 1.0
 * @date 2023 2023/6/19 21:29
 * @see ActiveObjects
 */
public class ActiveMessageQueue {
    private final LinkedList<ActiveMessage> messages = new LinkedList<>();

    public ActiveMessageQueue() {
        //同样启动 ActiveDaemonThread
        new ActiveDaemonThread(this).start();
    }

    public void offer(ActiveMessage activeMessage) {
        synchronized (this) {
            messages.addLast(activeMessage);
            this.notify();
        }
    }

    public ActiveMessage take() {
        synchronized (this) {
            while (messages.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return messages.removeFirst();
        }
    }

}

/**
 * 动态代理的执行线程
 */
class ActiveDaemonThread extends Thread{
    private final ActiveMessageQueue queue;

    public ActiveDaemonThread(ActiveMessageQueue queue) {
        super("ActiveThread");
        this.queue = queue;
        // 设置线程名称 和守护线程
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true){
            ActiveMessage message = this.queue.take();
            message.execute();
        }
    }
}

