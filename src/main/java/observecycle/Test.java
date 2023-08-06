package observecycle;

import java.util.concurrent.TimeUnit;

/**
 * @author 邱星晨
 */
public class Test {
    public static void main(String[] args) {
        Observable observable = new ObservableThread<Integer>(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
        observable.start();
    }
}

class Test2 {
    public static Integer ADDNUM = 1000;

    public static void main(String[] args) {
        TaskLifeCycle<Integer> taskLifeCycle = new EmptyLifeCycle<>() {
            @Override
            public void onFinish(Thread thread, Integer result) {
                System.out.println("相加值=" + result);
            }
        };
        Observable observable = new ObservableThread<>(taskLifeCycle, () -> {
            int ans = 0;
            for (int i = 0; i < ADDNUM; i++) {
                ans += i;
            }
            return ans;
        });
        observable.start();
    }
}

class Test3 {
    static class Add {
        int a = 0;
        int b = 1000;

        public Integer add() {
            int ans = 0;
            for (int i = a; i < b; i++) {
                ans += i;
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        TaskLifeCycle<Integer> taskLifeCycle = new EmptyLifeCycle<>() {
            @Override
            public void onFinish(Thread thread, Integer result) {
                System.out.println("相加值=" + result);
            }
        };
        Task<Integer> task = () -> {
            Add add = new Add();
            return add.add();
        };
        Observable observable = new ObservableThread<>(taskLifeCycle, task);
        observable.start();
    }
}