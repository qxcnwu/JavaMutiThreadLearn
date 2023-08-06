/**
 * @Author qxc
 * @Date 2023 2023/6/14 11:13
 * @Version 1.0
 * @PACKAGE PACKAGE_NAME
 */
public class T2 {
    public static final OK ok=new OK();
    public static void main2(String[] args) {
        ok.setB(1);
        System.out.println(ok);
    }

    public static void main3(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException("");
    }

    public static void main(String[] args) {

    }
}

class OK {
    public int a;
    public int b;
    public final static int qq = 1;
    private static int q = 10;

    public OK() {
        a = 0;
        b = 1;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public static int getQ() {
        return q;
    }

    public static void setQ(int q) {
        OK.q = q;
    }

    @Override
    public String toString() {
        return "OK{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}

