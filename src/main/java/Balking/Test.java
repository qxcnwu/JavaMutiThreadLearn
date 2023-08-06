package Balking;

/**
 * @author 邱星晨
 */
public class Test {
    public static void main(String[] args) {
        final DocumentThread documentThread = new DocumentThread("D:\\JAVA\\Th\\src\\main\\java\\Balking", "test.txt");
        documentThread.start();
//        Runtime.getRuntime().addShutdownHook();
    }
}
