
public class JavaClassLoaderLearn {
    public static void main(String[] args) {

    }
}

/**
 * 跟类加载器是最顶层的类加载器，没有任何的父类加载器
 * 负责核心类库的加载
 */
class RootClassLoader{
    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(System.getProperty("sun.boot.library.path"));
        System.out.println(System.getProperty("java"));
        System.out.println(System.getProperties());
    }
}

/**
 * 扩展类加载器，加载JAVA_HOME下的目录
 */
class extendClassLoader{
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.ext.dirs"));
    }
}

/**
 * 系统类加载器加载classpath下的类库资源
 */
class systemclassloader{
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(systemclassloader.class.getClassLoader());
    }
}