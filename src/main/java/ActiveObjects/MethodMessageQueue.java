package ActiveObjects;

import java.util.LinkedList;

/**
 * 类似 流水线模式的传送带 将proxy传过来的MethodMessage  开辟线程执行掉
 * @author 邱星晨
 */
public class MethodMessageQueue {
    // 用于存放提交的MethodMessage信息
    private final LinkedList<MethodMessage> messages = new LinkedList<>();

    public MethodMessageQueue(){
        // 启动线程
        new MethodDaemonThread(this).start();
    }

    // 添加
    public void offer(MethodMessage methodMessage){
        synchronized (this){
            messages.addLast(methodMessage);
            // 这里只设置一个线程执行任务
            this.notify();
        }
    }
    // 弹出
    protected MethodMessage take(){
        synchronized (this){
            // queue没有MethodMessage时 挂起阻塞
            while (messages.isEmpty()){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 有MethodMessage 则弹出第一个
            return messages.removeFirst();
        }
    }
}
