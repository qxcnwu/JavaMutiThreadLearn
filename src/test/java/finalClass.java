/**
 * 外部final类修饰符
 */
public final class finalClass {
}

final class c2 {
}


/**
 * 内部类修饰符号
 */
final class Parent2 {
    /**
     * 内部成员final类
     * public protected default private
     */
    public final class a {
    }

    protected final class c {
    }

    final class b {
    }

    final private class d {
    }

    {
        final class a1 {
        }
    }

    /**
     * 局部内部final类修饰符
     * default
     */
    public void creat() {
        final class a {
        }
    }

    interface HelloWorld {
        public void greet();

        public void greetSomeone(String someone);
    }

    /**
     * 匿名内部final类不能拥有修饰符
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
     * 内部静态final类修饰符
     * public protected default private
     */
    public final static class c1{}
    protected final static class c2{}
    static final class c3{}
    private final static class c4{}
}