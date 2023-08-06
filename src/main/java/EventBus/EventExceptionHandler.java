package EventBus;

/**
 * @author 邱星晨
 */
public interface EventExceptionHandler {
    void handle(Throwable th,EventContext eventContext);
}
