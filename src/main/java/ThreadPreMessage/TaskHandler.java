package ThreadPreMessage;

import java.util.concurrent.TimeUnit;
import static java.util.concurrent.ThreadLocalRandom.current;

public class TaskHandler extends  Thread{
    private final Request request;

    public TaskHandler(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        System.out.println(request+"start");
        slow();
        System.out.println(request+"done");
    }

    private void slow(){
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
