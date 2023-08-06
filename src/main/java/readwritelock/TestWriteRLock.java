package readwritelock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 邱星晨
 */
class ShareData {
    private final List<Character> container = new ArrayList<>();
    private final ReadWriteLock readWriteLock = ReadWriteLock.readWriteLock();
    private final Lock wLock = readWriteLock.writeLock();
    private final Lock rLock = readWriteLock.readLock();
    private final Integer len;

    public ShareData(Integer len) {
        this.len = len;
        for (int i = 0; i < len; i++) {
            container.add(i, 'z');
        }
    }

    public char[] read() throws InterruptedException {
        try {
            rLock.lock();
            char[] buff = new char[len];
            for (int i = 0; i < len; i++) {
                buff[i] = container.get(i);
            }
            TimeUnit.SECONDS.sleep(100);
            return buff;
        } finally {
            rLock.unLock();
        }
    }

    public void write(Character c) throws InterruptedException {
        try {
            wLock.lock();
            for (int i = 0; i < len; i++) {
                container.add(i, c);
            }
            TimeUnit.SECONDS.sleep(1);
        } finally {
            wLock.unLock();
        }
    }
}

/**
 * @author 邱星晨
 */
public class TestWriteRLock {
    public static void main(String[] args) {
        final ShareData shareData = new ShareData(10);
        String test = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < test.length(); j++) {
                    try {
                        shareData.write(test.charAt(j));
                        System.out.println(Thread.currentThread() + " add " + test.charAt(j));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(Thread.currentThread() + "read" + new String(shareData.read()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

class test {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arr.add(i);
        }
        final Stream<Integer> q = arr.stream().map(i -> {
            ++i;
            System.out.println(i);
            return i + 1;
        });
        System.out.println(arr);
        System.out.println(q.distinct().collect(Collectors.toList()));
    }
}
