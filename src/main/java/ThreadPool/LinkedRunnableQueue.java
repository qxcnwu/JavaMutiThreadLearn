package ThreadPool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue{
    //最大限度
    private final int limit;
    //若队列中的线程满了则需要执行拒绝策略
    private final DenyPolice denyPolice;
    //存放任务的队列
    private final LinkedList<Runnable> runnables=new LinkedList<>();
    private ThreadPool threadPool;

    /**
     * 初始化构造方法
     * @param limit 最大任务数量
     * @param denyPolice 拒绝策略
     * @param threadPool 线程池
     */
    public LinkedRunnableQueue(int limit,DenyPolice denyPolice,ThreadPool threadPool){
        this.limit=limit;
        this.denyPolice=denyPolice;
        this.threadPool=threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnables){
            if(runnables.size()>=limit){
                //执行拒绝策略
                denyPolice.reject(runnable,threadPool);
            }else{
                //可以执行放入队列，唤醒所有等待线程
                runnables.addLast(runnable);
                runnables.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException{
        synchronized (runnables){
            while (runnables.isEmpty()){
                try{
                    //如果其中没有线程进入等待
                    runnables.wait();
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            return runnables.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (runnables){
            return runnables.size();
        }
    }
}
