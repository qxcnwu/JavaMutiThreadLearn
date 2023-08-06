package EventBus;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author 邱星晨
 */
public class FileChangeEvent {
    private final Path path;
    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path child, WatchEvent.Kind<?> kind) {
        path=child;
        this.kind=kind;
    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }
}
