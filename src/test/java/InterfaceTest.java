import java.util.Properties;

/**
 * @Author qxc
 * @Date 2023 2023/7/1 12:02
 * @Version 1.0
 * @PACKAGE PACKAGE_NAME
 */
public interface InterfaceTest {
}

interface a{}

interface a1{}

abstract interface a2{}


class q{
    public interface a{}
    protected interface b{}
    interface c{}
    private interface d{}

    public static interface a1{}
    protected static interface b11{}
    static interface c1{}
    private static interface d1{}
}

class Test {
    public static void main(String args[]) {
        String s = "祝你考出好成绩! ";
        System.out.println(s.length());
    }
}

class Chinese{
    private static Chinese objref =new Chinese();
    private Chinese(){}
    public static Chinese getInstance() { return objref; }
}

class TestChinese {
    public static void main(String [] args) {

    }
}