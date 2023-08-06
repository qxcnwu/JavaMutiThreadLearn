package Balking;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author 邱星晨
 */
public class DocumentThread extends Thread{
    private final String documentPath;
    private final String documentName;
    private final static Scanner SCANNER =new Scanner(System.in);

    public DocumentThread(String documentPath,String documentName){
        this.documentName=documentName;
        this.documentPath=documentPath;
    }

    @Override
    public void run() {
        int times=0;
        try {
            Document document=Document.create(documentPath,documentName);
            while(true){
                String text=SCANNER.next();
                if("quit".equals(text)){
                    document.close();
                    break;
                }
                document.edit(text);
                if(times==5){
                    document.save();
                    times=0;
                }
                times++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
