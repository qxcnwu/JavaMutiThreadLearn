package twophaseterminal;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.net.Socket;

/**
 * @author 邱星晨
 */
public class Referance {
    private final byte[] BYTES = new byte[2 << 19];

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " will be GC");
    }
}


class SocketCleaningTracker {
    private static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();

    /*
      初始化前就启动清除线程
     */
    static {
        Cleaner.getInstance().start();
    }

    /**
     * 创建新的Tracker对象
     * @param socket
     */
    private static void track(Socket socket) {
        new Tracker(socket, QUEUE);
    }

    /**
     * 工厂模式创建单例CLEAN对象
     * 由Clean对queue中的内容进行关闭
     */
    private final static class Cleaner extends Thread {
        private Cleaner() {
            setName("clean");
            setDaemon(true);
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    Tracker tracker = (Tracker) QUEUE.remove();
                    tracker.close();
                } catch (InterruptedException ignored) {

                }
            }
        }

        private static class Holder {
            private final static SocketCleaningTracker.Cleaner INSTANCE = new SocketCleaningTracker.Cleaner();
        }

        public static Cleaner getInstance() {
            return Holder.INSTANCE;
        }
    }

    /**
     * 继承PhantomReference
     * PhantomReference决定回收前将其加入queue
     */
    private static class Tracker extends PhantomReference<Object> {
        Socket socket;

        /**
         * Creates a new phantom reference that refers to the given object and
         * is registered with the given queue.
         *
         * <p> It is possible to create a phantom reference with a {@code null}
         * queue, but such a reference is completely useless: Its {@code get}
         * method will always return {@code null} and, since it does not have a queue,
         * it will never be enqueued.
         *
         * @param referent the object the new phantom reference will refer to
         * @param q        the queue with which the reference is to be registered,
         *                 or {@code null} if registration is not required
         */
        public Tracker(Socket referent, ReferenceQueue<? super Object> q) {
            super(referent, q);
            this.socket = referent;
        }

        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}