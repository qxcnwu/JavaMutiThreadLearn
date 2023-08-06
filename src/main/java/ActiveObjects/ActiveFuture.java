package ActiveObjects;

import future.FutureTask;

/**
 * 返回凭据
 *
 * @author 邱星晨
 * @param <T>
 */
public class ActiveFuture<T> extends FutureTask<T> {
    /**
    将方法权限 改大 由protected 改成 public  方便调用
    */
    @Override
    public void finish(T result) {
        super.finish(result);
    }
}
