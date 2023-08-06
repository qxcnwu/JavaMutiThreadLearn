import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainTest {
    public static void main1(String[] args) {
        Thread.currentThread().setName("呦西");
        TestThreading testThreading=new TestThreading();
        testThreading.setName("asd");
        testThreading.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+i);
        }
    }

    public static void main2(String[] args) {
        for (int i = 0; i < 2; i++) {
            Tickets tickets=new Tickets(String.valueOf(i));
            tickets.start();
        }
    }

    public static void main4(String[] args) {
        ThreadImp threadImp=new ThreadImp();
        Thread th=new Thread(threadImp);
        th.start();
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
        }
    }

    public static void main5(String[] args) {
        TicketsImp tickets=new TicketsImp();
        for (int i = 0; i < 1; i++) {
            Thread th=new Thread(tickets);
            th.start();
        }
    }

    public static void main6(String[] args) {
        ThreadCal threadCal=new ThreadCal();
        FutureTask<Integer> ft=new FutureTask<>(threadCal);
        Thread tt=new Thread(ft);
        tt.start();
        try {
            Integer inr=ft.get();
            System.out.println(inr);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main7(String[] args) throws InterruptedException {
        ThreadPro2 th2=new ThreadPro2();
        th2.setPriority(1);
        th2.setDaemon(true);
        th2.start();
        // th2.join();
        for (int i = 0; i < 5 ; i++) {
            System.out.println(i);
            th2.stop();
        }
    }

    public static void main8(String[] args) {
        ThreadSafe5 threadSafe456=new ThreadSafe5();
        for (int i = 0; i < 100; i++) {
            ThreadSafe5 threadSafe=new ThreadSafe5();
//            threadSafe456.start();
            Thread th=new Thread(threadSafe);
            th.start();
        }
    }

    public static void main(String[] args) {
        Product p=new Product();
        for (int i = 0; i < 1; i++) {
            ProducerThread t=new ProducerThread(p);
            t.start();
            CustomerThread c=new CustomerThread(p);
            c.start();
        }
    }
}
