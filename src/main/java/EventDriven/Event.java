package EventDriven;

/**
 * @author 邱星晨
 */
public class Event implements Message{
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
