package Balking;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 邱星晨
 */
public class AutoSaveThread extends Thread{

    private final Document document;
    public AutoSaveThread(Document document){
        super("Auto save");
        this.document=document;
    }

    @Override
    public void run() {
        while(true){
            try {
                document.save();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException | IOException e) {
                break;
            }
        }
    }
}
