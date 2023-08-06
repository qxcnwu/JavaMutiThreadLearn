/**
 * 外部abstract类修饰符
 */
public abstract class AbstractClass {
}

abstract class c22 {
}


/**
 * 内部类修饰符号
 */
abstract class Parent22 {
    /**
     * 内部成员abstract类
     * public protected default private
     */
    public abstract class a {
    }

    protected abstract class c {
    }

    abstract class b {
    }

    abstract private class d {
    }

    {
        abstract class a1 {
        }
    }

    /**
     * 局部内部abstract类修饰符
     * default
     */
    public void creat() {
        abstract class a {
        }
    }

    interface HelloWorld {
        public void greet();

        public void greetSomeone(String someone);
    }

    /**
     * 匿名内部abstract类不能拥有修饰符
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
     * 内部静态abstract类修饰符
     * public protected default private
     */
    public abstract static class c1{}
    protected abstract static class c2{}
    static abstract class c3{}
    private abstract static class c4{}
}