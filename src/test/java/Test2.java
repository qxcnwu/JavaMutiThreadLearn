/**
 * 外部类的修饰符
 * public default
 * 外部final类修饰符
 */
public class Test2 {
}

class c {
}

/**
 * 内部类修饰符号
 */
class Parent {
    /**
     * 内部成员类
     * public protected default private
     */
    public class a {
    }

    protected class c {
    }

    class b {
    }

    private class d {
    }

    {
        class a1 {
        }
    }

    /**
     * 局部内部类修饰符
     * default
     */
    public void creat() {
        class a {
        }
    }

    interface HelloWorld {
        public void greet();

        public void greetSomeone(String someone);
    }

    /**
     * 匿名内部类不能拥有修饰符
     */
    public void creat2() {
        HelloWorld h = new HelloWorld() {
            @Override
            public void greet() {

            }

            @Override
            public void greetSomeone(String someone) {

            }
        };
    }

    /**
     * 内部静态类修饰符
     * public protected default private
     */
    public static class c1{}
    protected static class c2{}
    static class c3{}
    private static class c4{}
}