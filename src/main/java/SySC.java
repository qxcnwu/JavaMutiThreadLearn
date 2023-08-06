public class SySC {
    public synchronized void test(){
        try{
            Thread.sleep(60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SySC sySC=new SySC();
        Thread thd1=new Thread(sySC::test);
        thd1.start();
        Thread.sleep(12);
        Thread thd2=new Thread(sySC::test);
        thd2.start();
        Thread.sleep(0);
        System.out.println(thd2.getState());
    }
}