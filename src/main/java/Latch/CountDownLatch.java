package Latch;

import future.Callback;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 邱星晨
 */
public class CountDownLatch extends Latch {
    private Runnable runnable = null;

    public CountDownLatch(int limit) {
        super(limit);
    }

    public CountDownLatch(int limit, Runnable runnable) {
        this(limit);
        this.runnable = runnable;
    }

    @Override
    public void await() throws InterruptedException {
        synchronized (this) {
            while (limit > 0) {
                this.wait();
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override
    public void await(TimeUnit timeUnit, long time) throws InterruptedException, TimeoutException {
        if (time < 0) {
            throw new InterruptedException("time invalid");
        }
        long re = timeUnit.toNanos(time);
        final long end = System.nanoTime() + re;
        synchronized (this) {
            while (limit > 0) {
                if (TimeUnit.NANOSECONDS.toMillis(re) <= 0) {
                    throw new TimeoutException("超时");
                }
                this.wait(TimeUnit.NANOSECONDS.toMillis(re));
                re = end - System.nanoTime();
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override
    public void countDown() {
        synchronized (this) {
            if (limit <= 0) {
                throw new IllegalStateException("all task already done");
            }
            limit--;
            this.notifyAll();
        }
    }


    @Override
    public int getUnarrived() {
        return limit;
    }
}
