package twophaseterminal;

import java.util.concurrent.TimeUnit;

/**
 * @author 邱星晨
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        SoftLRUCache<Integer,Referance> lruChanel=new SoftLRUCache<>(200,key->new Referance());
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            lruChanel.get(i);
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println(i);
        }
    }
}
