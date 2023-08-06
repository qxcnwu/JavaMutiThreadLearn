package EventBus;


import org.openjdk.jol.vm.*;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 邱星晨
 */
public class Test {
    public void main() {

        /*
          同步
         */
        Bus bus = new EventBus("TestBus");
        bus.register(new SimpleObject());
        bus.register(new SimpleObject());
        bus.post(12);
        System.out.println("+++++++++++++++");
        bus.post(1323, "test");
        System.out.println("------------------");
        /*
          异步
         */
        Bus bus2 = new AsyncEventBus("TestBus", (ThreadPoolExecutor) Executors.newFixedThreadPool(10));
        bus2.register(new SimpleObject());
        bus2.post(123);
        System.out.println("--------");
        bus2.post(321, "test");
    }

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        final EventBus eventBus = new AsyncEventBus(threadPoolExecutor);
        eventBus.register(new FileChangeListen());
        final DirectoryTargetMonitor directoryTargetMonitor = new DirectoryTargetMonitor(eventBus, "D:\\desktop");
        directoryTargetMonitor.startMonitor();
    }
}

class FileChangeListen {
    @Subscribe
    public void change(FileChangeEvent fileChangeEvent) {
        System.out.println(fileChangeEvent.getPath() + "-" + fileChangeEvent.getKind());
    }
}

class TestParam {
    public String name = "qxc";
    public int age = 16;

    public static void main(String[] args) {
        // 传递类型是引用类型时,原始对象发生改变
        TestParam t = new TestParam();
        System.out.println("t address:" + VM.current().addressOf(t));
        change(t);

        // 传递类型是引用类型时,重新生成对象，未发生改变
        t = new TestParam();
        System.out.println("t address:" + VM.current().addressOf(t));
        change2(t);

        // 传递类型是包装类型 String Integer 重新生成对象
        String s = "qwe";
        System.out.println("s address:" + VM.current().addressOf(s));
        change(s);

        // 传递类型是包装类型 String Integer 重新生成对象
        s = "qwe";
        System.out.println("s address:" + VM.current().addressOf(s));
        change2(s);

        // int类型直接更改
        int q = 0;
        System.out.println("q address:" + VM.current().addressOf(q));
        change(q);

        // 重新生成对象
        q = 0;
        System.out.println("q address:" + VM.current().addressOf(q));
        change2(q);

    }

    public static void change(TestParam t) {
        System.out.println("change t address before:" + VM.current().addressOf(t));
        t.name = "zj";
        t.age = 20;
        System.out.println("change t address after:" + VM.current().addressOf(t));
    }

    public static void change2(TestParam t) {
        System.out.println("change2 t address before:" + VM.current().addressOf(t));
        t = new TestParam();
        t.name = "zj2";
        t.age = 200;
        System.out.println("change2 t address after:" + VM.current().addressOf(t));
    }

    public static void change(String t) {
        System.out.println("change string t address before:" + VM.current().addressOf(t));
        t = "qxc";
        System.out.println("change string t address after:" + VM.current().addressOf(t));
    }

    public static void change2(String t) {
        System.out.println("change2 string t address before:" + VM.current().addressOf(t));
        t = new String("qxc");
        System.out.println("change2 string t address after:" + VM.current().addressOf(t));
    }

    public static void change(int t) {
        System.out.println("change int q address before:" + VM.current().addressOf(t));
        t = 3;
        System.out.println("change int q address after:" + VM.current().addressOf(t));
    }

    public static void change2(int t) {
        System.out.println("change2 int q address before:" + VM.current().addressOf(t));
        t = new Integer(6);
        System.out.println("change2 int q address after:" + VM.current().addressOf(t));
    }

    @Override
    public String toString() {
        return "TestParam{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Solution {
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length;
        int g = n / k;
        int[] memo = new int[1 << n];
        Arrays.fill(memo, -1);
        memo[0] = 0;
        for (int i = 1; i < 1 << n; i++) {
            if (Integer.bitCount(i) == g) {
                HashSet<Integer> set = new HashSet<>();
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                for (int j = 0; j < n; j++) {
                    if ((1 & (i >> j)) != 0) {
                        set.add(nums[j]);
                        min = Math.min(min, nums[j]);
                        max = Math.max(max, nums[j]);
                    }
                }
                memo[i] = set.size() != g ? -1 : max - min;
            }
        }
        int ans = 0;
        int[] f = new int[1 << n];
        Arrays.fill(f, 1 << n);
        f[0] = 0;
        for (int i = 0; i < 1 << n; i++) {
            if (Integer.bitCount(i) % g == 0) {
                for (int j = i; j > 0; j = (j - 1) & i) {
                    if (memo[j] == -1) {
                        continue;
                    }
                    f[i] = Math.min(f[i], f[i - j] + memo[j]);
                }
            }
        }
        return f[(1 << n) - 1];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
    }
}

class P {
    public static int abc = 123;

    static {
        System.out.println("P is init");
    }
}

class S extends P {
    public static int abc1 = 1231;

    static {
        System.out.println("S is init");
    }
}

class Tests {
    public static void main(String[] args) {
        System.out.println(S.abc1);
    }
}

class Demo {
    public static void main(String[] args) {
        Collection<?>[] collections =
                {new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>().values()};
        Super subToSuper = new Sub();
        for (Collection<?> collection : collections) {
            System.out.println(subToSuper.getType(collection));
        }
    }

    abstract static class Super {
        public static String getType(Collection<?> collection) {
            return "Super: collection";
        }

        public static String getType(List<?> list) {
            return "Super: list";
        }


        public static String getType(ArrayList<?> list) {
            return "Super: arrayList";
        }

        public static String getType(Set<?> set) {
            return "Super: set";
        }

        public static String getType(HashSet<?> set) {
            return "Super: hashSet";
        }
    }

    static class Sub extends Super {
        public static String getType(Collection<?> collection) {
            return "Sub";
        }
    }
}

abstract class Parent {
    private void m1() {
    }

    void m2() {
    }

    protected void m3() {
    }

    public static void m4() {
    }
}

abstract class Parent2 {
    private void m1() {
    }

    void m2() {
    }

    protected void m3() {
    }

    public static void m4() {
    }
}


class son extends Parent {
    public static void main(String[] args) {
        int x = 0;
        switch (x) {
            default:
                System.out.println("Hello");
        }
        String _FirstApplet = "12312";
    }
}


class Test3 {
    public static void main(String args[]) {
        System.out.println(100 % 3);
        System.out.println(100 % 3.0);
    }
}