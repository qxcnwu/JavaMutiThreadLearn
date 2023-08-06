package ThreadPreMessage;

/**
 * @author 邱星晨
 */
public class Request {
    private final String msg;

    public Request(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
