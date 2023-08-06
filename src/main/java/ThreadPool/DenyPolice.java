package ThreadPool;

public interface DenyPolice {
    void reject(Runnable runnable,ThreadPool threadPool);

    /**
     * 该策略将任务抛弃
     */
    class DiscardDenyPolice implements DenyPolice{
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {

        }
    }

    /**
     * 该策略将任务抛出异常
     */
    class AbortDenyPolice implements DenyPolice{
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            throw new RunnableDenyException(runnable+" will be abort");
        }
    }

    /**
     * 该策略是任务在提交者所在线程中执行
     */
    class RunnerDenyPolice implements DenyPolice{
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            if (!threadPool.isShutDown()){
                runnable.run();
            }
        }
    }
}
