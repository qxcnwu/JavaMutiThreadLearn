package ClassLoaders;

import java.util.ArrayList;
import java.util.List;

/**
 * 继承原有自定义类加载器，更新破坏双清委托机制
 */
public class ClassLoaderPlus extends classLoaders {
    public ClassLoaderPlus(String s) {
        super(s);
    }

    public ClassLoaderPlus() {

    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            //如果已经加载则直接返回
            Class<?> klass = findLoadedClass(name);
            if (klass == null) {
                //如果是系统类则调用系统类加载器
                if (name.startsWith("java") || name.startsWith("javax")) {
                    try {
                        klass = getSystemClassLoader().loadClass(name);
                    } catch (Exception ignored) {

                    }
                } else {
                    //如果不是系统类则使用自定义类加载器进行加载
                    try {
                        klass = this.findClass(name);
                    } catch (ClassNotFoundException ignored) {

                    }
                    if (klass == null) {
                        //调用其父类加载器进行加载
                        if (getParent() != null) {
                            klass = getParent().loadClass(name);
                        } else {
                            //父类加载器无法加载则调用系统加载器加载
                            klass = getSystemClassLoader().loadClass(name);
                        }
                    }
                }
            }
            //多次加载还未完成则无法加载
            if (klass == null) {
                throw new ClassNotFoundException("no such class " + name);
            }
            if (resolve) {
                resolveClass(klass);
            }
            return klass;
        }
    }
}
