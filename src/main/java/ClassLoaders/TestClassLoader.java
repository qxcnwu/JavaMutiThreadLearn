package ClassLoaders;

import jdk.dynalink.Namespace;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.lang.String;

/**
 * 双亲继承机制
 * 首先是自定义加载器-系统类加载器-扩展类加载器-跟类加载器
 * 想要绕过其中的系统类加载器 需要将其父类加载器设置为NULL
 * 或者将自定义类加载器的父加载器设置为扩展类加载器
 */
public class TestClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        test();

        classLoaders cld = new classLoaders("D:/JAVA/Th/Class/", null);
        Class<?> clz = cld.findClass("ClassLoaders.helloworld");
        System.out.println(clz.getClassLoader());

        Object helloWorld = clz.getDeclaredConstructor().newInstance();
        System.out.println(helloWorld);

        Method method = clz.getMethod("welcome");
        String str = (String) method.invoke(helloWorld);
        System.out.println(str);
    }

    // 扩展类加载器
    public static void test() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ClassLoader ls = classLoaders.class.getClassLoader();
        classLoaders cld = new classLoaders("D:/JAVA/Th/Class/", ls);
        Class<?> clz = cld.loadClass("ClassLoaders.helloworld");
        System.out.println(clz.getClassLoader());

        Object helloWorld = clz.getDeclaredConstructor().newInstance();
        System.out.println(helloWorld);

        Method method = clz.getMethod("welcome");
        String str = (String) method.invoke(helloWorld);
        System.out.println(str);
    }
}

class Test2 {
    /**
     * 不同的类加载器加载同一个路径得到的是不同对象
     * 相同的类加载器同一个路径得到的是不同对象
     * 不同运行时包不可以相互访问
     *
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader ls = classLoaders.class.getClassLoader();
        classLoaders cld = new classLoaders("D:/JAVA/Th/Class/");
        Class<?> clz = cld.loadClass("ClassLoaders.helloworld");

        ClassLoader ls2 = classLoaders.class.getClassLoader();
        classLoaders cld2 = new classLoaders("D:/JAVA/Th/Class/");
        Class<?> clz3 = cld2.loadClass("ClassLoaders.helloworld");

        System.out.println(clz);
        System.out.println(clz3);
        System.out.println(clz == clz3);
    }
}

/**
 * 对于任意的类加载器实例其不会多次加载同一个类一个类只能加载一次
 * 不同的类加载器可以加载多个
 * 不同累计在其加载同一个类生成两个实例
 * 相同类加载器加载同一个class则两个对象
 * 相同类加载器的不同实例可以加载同一个类分别得到不同的实例
 */
class namespaceTest {
    public static void main(String[] args) {
        ClassLoader ls = namespaceTest.class.getClassLoader();
        System.out.println(ls);
        Class<?> a = null;
        try {
            a = ls.loadClass(namespaceTest.class.getCanonicalName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?> b = null;
        ClassLoaderPlus cls = new ClassLoaderPlus("");
        try {
            b = cls.loadClass(namespaceTest.class.getCanonicalName(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ClassLoader l = ClassLoader.getSystemClassLoader();
        Class<?> c = null;
        try {
            c = l.loadClass(namespaceTest.class.getCanonicalName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(a == b);
        System.out.println(c == b);
    }
}

/**
 * 自定义类加载器在加载过程中维护了String.class，因此可以正常访问java.lang.String
 * 在JVM中所有参与的类加载器都会成为此类的初始化加载器
 * 因此在自定义、系统、额外、根加载器中都会维护String
 * jvm回收class需要满足以下三个条件
 * 实例GC、父加载器GC、该类的class实例没在其他地方引用
 */
class testClass {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassLoaderPlus clp = new ClassLoaderPlus("D:/java/th/dir/");
        Class<?> aClass = clp.loadClass("ClassLoaders.Test");
        System.out.println(clp.getParent());
        aClass.getDeclaredConstructor().newInstance();
    }
}

class txt {
    public static void main(String[] args) {
        System.getProperty("");
    }
}