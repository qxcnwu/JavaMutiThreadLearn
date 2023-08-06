import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ThreadGroupLearn {
    public static void main(String[] args) {
        ThreadGroup rootG=Thread.currentThread().getThreadGroup();
        ThreadGroup group1=new ThreadGroup("group1");
        System.out.println(group1.getParent()==rootG);
        ThreadGroup group2=new ThreadGroup(group1,"group2");
        System.out.println(group2.getParent()==group1);
    }
}

/**
 * enumerate递归显示活跃进程
 * 如果加上参数false则不进行递归操作
 */
class ThreadTest{
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup gp=new ThreadGroup("gp");
        Thread thd=new Thread(gp,()->{
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"tgread");
        thd.start();
        Thread.sleep(100);
        ThreadGroup mainGp=Thread.currentThread().getThreadGroup();
        System.out.println(mainGp.activeCount());
        Thread[] thdlist=new Thread[mainGp.activeCount()];
        int enus= mainGp.enumerate(thdlist);
        System.out.println(enus);
        //不计算不包含 gp中的线程未进行递归操作
        System.out.println(mainGp.enumerate(thdlist,false));

        Arrays.stream(thdlist).forEach(System.out::println);

        gp.list();

        //判断其夫线程组是否为gp自己是自己的爹
        System.out.println(gp.parentOf(gp));
        //递归中断线程组中的线程
        //gp.interrupt();
        //TimeUnit.SECONDS.sleep(1);
        System.out.println("活跃线程数量"+gp.activeCount());

        //销毁线程组
        gp.destroy();
        System.out.println(gp.isDestroyed());

    }
}

/**
 * 线程组销毁方法
 */
class destroyGp{
    public static void main(String[] args) {
        ThreadGroup gp=new ThreadGroup("gp");
        ThreadGroup maingp=Thread.currentThread().getThreadGroup();
        maingp.list();
        gp.destroy();
        maingp.list();
        System.out.println(gp.isDestroyed());
    }
}

/**
 * 设置守护线程组，当前线程组没有活动线程的时候
 * 该线程组自动销毁
 */
class setdeo{
    public static void main(String[] args) {
        ThreadGroup t1=new ThreadGroup("t1");
        ThreadGroup t2=new ThreadGroup("t2");
        t2.setDaemon(true);
        Thread th1=new Thread(t1,()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread th2=new Thread(t2,()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th1.start();;
        th2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t1.isDestroyed());
        System.out.println(t2.isDestroyed());
    }

}