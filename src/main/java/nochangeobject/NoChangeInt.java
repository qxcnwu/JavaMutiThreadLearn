package nochangeobject;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author 邱星晨
 */
public class NoChangeInt {
    private int init;
    public NoChangeInt(int init){this.init=init;}
    public int add(int i){
        init+=i;
        return init;
    }
    public int getInit(){return init;}

    public static void main(String[] args) {
        NoChangeInt noChangeInt=new NoChangeInt(0);
        IntStream.range(0,3).forEach(i->new Thread(()->{
            int inc=0;
            while(true){
                int oldValue;
                int result;
                //添加对共享变量的锁保护线程不出错
                synchronized (NoChangeInt.class){
                    oldValue= noChangeInt.getInit();
                    result=noChangeInt.add(inc);
                }
                System.out.println(oldValue+"+"+inc+"="+result);
                if(inc+oldValue!=result){
                    System.out.println("error");
                }
                inc++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}

final class IntegerAccumulator{
    private final int init;
    public IntegerAccumulator(int init){
        this.init=init;
    }
    public IntegerAccumulator(IntegerAccumulator ita,int init){
        this.init=init+ita.getInit();
    }
    public int getInit(){return init;}
    /**
     * 构造新的累加器
     */
    public IntegerAccumulator add(int i){
        return new IntegerAccumulator(this,i);
    }
    public static void main(String[] args) {
        IntegerAccumulator noChangeInt=new IntegerAccumulator(0);
        IntStream.range(0,3).forEach(i->new Thread(()->{
            int inc=0;
            while(true){
                int oldValue= noChangeInt.getInit();
                int result=noChangeInt.add(inc).getInit();
                System.out.println(oldValue+"+"+inc+"="+result);
                if(inc+oldValue!=result){
                    System.out.println("error");
                }
                inc++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
