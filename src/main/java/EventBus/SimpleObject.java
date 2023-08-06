package EventBus;

/**
 * @author 邱星晨
 */
public class SimpleObject {
    @Subscribe
    public void test2(Integer x){
        System.out.println(x+"asd");
    }

    @Subscribe(topic = "test")
    public void test3(Integer x){
        System.out.println(x+"lkj");
    }
}
