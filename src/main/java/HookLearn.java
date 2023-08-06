/**
 * java 向线程中添加hook钩子线程
 * 用来获取线程运行时的异常信息
 */
public class HookLearn {
}

/**
 * 捕获异常然后抛出
 * 线程自己会判断是否有异常处理接口
 * 如果有则抛出
 * 由父线程组则直接调用父线程组的获取方法
 * 捕获顺序 父线程组-》全局默认-》system.err
 */
class uncaughtEH{
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t,e)->{
            System.out.println(t.getName()+" occur error");
            e.printStackTrace();
        });
        final Thread thd=new Thread(()->{
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1/0);
        });
        thd.start();
    }
}

class hookTest{
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("th1 start");
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                System.out.println("th1 退出");
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("th2 start");
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                System.out.println("th2 退出");
            }
        });
    }
}
