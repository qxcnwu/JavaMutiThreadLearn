package ClassLoaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;import java.nio.file.Path;
import java.nio.file.Paths;

public class classLoaders extends ClassLoader{
    private final static Path Default_CLASS_DIR= Paths.get("D:","JAVA/Th/Class/");

    private final Path classDir;

    /**
    * 默认的class路径
    */
    public classLoaders(){
        super();
        this.classDir=Default_CLASS_DIR;
    }

    public classLoaders(String classDir){
        super();
        this.classDir=Paths.get(classDir);
    }

    /**
     * 指定路径同时指定父类加载器
     * @param classDir
     * @param classLoader
     */
    public classLoaders(String classDir,ClassLoader classLoader){
        super(classLoader);
        this.classDir=Paths.get(classDir);
    }

    /**
     * 重写findClass方法
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classByte=this.readClassBytes(name);
        if(classByte.length == 0){
            throw new ClassNotFoundException("can not load this class");
        }
        // 调用define方法定义class
        return this.defineClass(name,classByte,0, classByte.length);
    }

    /**
     * 将class文件读取到内存
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private byte[] readClassBytes(String name) throws ClassNotFoundException{
        String path=name.replace(".","/");
        Path classFullPath=classDir.resolve(Paths.get(path+".class"));
        if(!classFullPath.toFile().exists()){
            throw new ClassNotFoundException("no such class "+name+" in "+classFullPath);
        }
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        try {
            Files.copy(classFullPath,b);
            return b.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("load "+classFullPath+" occur error!!");
        }
    }

    @Override
    public String toString() {
        return "classLoaders{" +
                "classDir=" + classDir +
                '}';
    }
}
