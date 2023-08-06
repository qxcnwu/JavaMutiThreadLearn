package ClassLoaders;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private static byte[] buffer=new byte[8];
    private static String str="";
    private static List<String> list=new ArrayList<>();
    static {
        buffer[0]=(byte)1;
        str="Simple";
        list.add("element");
        System.out.println(buffer);
        System.out.println(str);
        System.out.println(list);
    }
}
