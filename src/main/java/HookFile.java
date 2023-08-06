import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HookFile {
    private final static String lock_path = "/home/qxc/locks/";
    private final static String name = ".lock";
    private final static String permission = "rw-------";

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The Program received kill SIGNAL");
            getLockFile().toFile().delete();
        }));
        checking();
        for (; ; ) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Program is Running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void checking() throws IOException {
        Path path = getLockFile();
        if (path.toFile().exists()) {
            throw new RuntimeException("Program already running");
        }
        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(permission);
        Files.createFile(path, PosixFilePermissions.asFileAttribute(permissions));
    }

    private static Path getLockFile() {
        return Paths.get(lock_path, name);
    }
}


class Solution {
    public int maxSumDivThree(int[] nums) {
        Arrays.sort(nums);
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            arr.add(new ArrayList<>());
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
            arr.get(num % 3).add(num);
        }
        if (sum % 3 == 0) {
            return sum;
        }
        // 余2
        if (sum % 3 == 2) {
            // 取一个余2
            int two = Integer.MAX_VALUE;
            if (arr.get(2).size() != 0) {
                two = arr.get(2).get(0);
            }
            // 取两个余1
            int one = Integer.MAX_VALUE;
            if (arr.get(1).size() >= 2) {
                one = arr.get(1).get(0) + arr.get(1).get(1);
            }
            return sum - Math.min(one, two);
        }
        // 余1
        // 取2个余2
        int two = Integer.MAX_VALUE;
        if (arr.get(2).size() >= 2) {
            two = arr.get(2).get(0) + arr.get(2).get(1);
        }
        // 取两个余1
        int one = Integer.MAX_VALUE;
        if (arr.get(1).size() >= 1) {
            one = arr.get(1).get(0);
        }
        return sum - Math.min(one, two);
    }
}