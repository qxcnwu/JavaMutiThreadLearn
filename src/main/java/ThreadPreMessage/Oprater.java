package ThreadPreMessage;

import ThreadPool.BasicThreadPool;
import ThreadPool.ThreadPool;

import java.util.stream.IntStream;

/**
 * @author 邱星晨
 */
public class Oprater {
    private final static ThreadPool THREAD_POOL =new BasicThreadPool(2,6,4,1000);

    public static void submit(String msg){
        TaskHandler taskHandler=new TaskHandler(new Request(msg));
        THREAD_POOL.execute(taskHandler);
    }

    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i->Oprater.submit(String.valueOf(i)));
    }
}
