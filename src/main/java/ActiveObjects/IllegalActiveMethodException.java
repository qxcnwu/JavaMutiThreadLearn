package ActiveObjects;

/**
 * @author 邱星晨
 * @version Version  1.0
 * @apiNote PACKAGE  ActiveObjects
 * IllegalActiveMethodException类主要用于对象没有返回Future对象报错
 */
public class IllegalActiveMethodException extends Exception{
    public IllegalActiveMethodException(String message){
        super(message);
    }
}
