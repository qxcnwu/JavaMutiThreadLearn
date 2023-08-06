package EventDriven;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author 邱星晨
 */
public class Events {
    private final String type;
    private final String data;

    public Events(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}

class Test{
    public static void handlerA(Events events){
        System.out.println(events.getData().toLowerCase());
    }

    public static void handlerB(Events events){
        System.out.println(events.getData().toUpperCase());
    }

    public static void main(String[] args) {
        HandleLoop handleLoop=new HandleLoop();
        new Thread(handleLoop).start();
        String[] list={"A","asd","asdad","A","A","sdwefvf"};
        Arrays.stream(list).parallel().forEach(i->{
            System.out.println("提娜佳"+i);
            handleLoop.add(new Events(i,i));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

class HandleLoop implements Runnable {
    private final static ConcurrentLinkedQueue<Events> queue=new ConcurrentLinkedQueue<>();

    public void add(Events events){
        synchronized (queue){
            queue.offer(events);
            queue.notifyAll();
        }
    }

    @Override
    public void run() {
        while(true){
            synchronized (queue){
                if (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(!queue.isEmpty()) {
                    final Events poll = queue.poll();
                    if("A".equals(poll.getType())){
                        Test.handlerA(poll);
                    }else{
                        Test.handlerB(poll);
                    }
                }
                queue.notifyAll();
            }
        }
    }
}