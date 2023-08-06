package EventBus;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * @author 邱星晨
 */
public class DirectoryTargetMonitor {
    private WatchService watchService;
    private final EventBus eventBus;
    private final Path path;
    private volatile boolean start=false;

    public DirectoryTargetMonitor(EventBus eventBus, String path) {
        this(eventBus,path,"");
    }

    public DirectoryTargetMonitor (EventBus eventBus, String path,String... morePath) {
        this.eventBus = eventBus;
        this.path= Paths.get(path,morePath);
    }

    public void startMonitor() throws IOException {
        this.watchService= FileSystems.getDefault().newWatchService();
        this.path.register(watchService,StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_CREATE);
        System.out.printf("this [%s] is monitor\n",path);
        start=true;
        while (start){
            WatchKey watchKey=null;
            try {
                watchKey=watchService.take();
                watchKey.pollEvents().forEach(event ->{
                    WatchEvent.Kind<?> kind=event.kind();
                    Path path= (Path) event.context();
                    Path child=DirectoryTargetMonitor.this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child,kind));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.start=false;
            }finally {
                if (watchKey!=null) {
                    watchKey.reset();
                }
            }
        }
    }

    public void stopMonitor() throws IOException {
        System.out.println("stopping");
        Thread.currentThread().interrupt();
        this.start=false;
        this.watchService.close();
        System.out.println("stop ed");
    }
}
