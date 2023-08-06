package Balking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱星晨
 */
public class Document {
    private boolean changed = false;
    private final List<String> content = new ArrayList<>();
    private final FileWriter fileWriter;
    private static AutoSaveThread autoSaveThread;

    private Document(String documentPath, String documentName) throws IOException {
        fileWriter = new FileWriter(new File(documentPath, documentName));
    }

    public static Document create(String documentPath, String documentName) throws IOException {
        Document document = new Document(documentPath, documentName);
        autoSaveThread = new AutoSaveThread(document);
        autoSaveThread.start();
        return document;
    }

    /**
     * 写入文件
     * @param content
     */
    public void edit(String content) {
        synchronized (this) {
            this.content.add(content);
            this.changed = true;
        }
    }

    /**
     * 关闭写入
     * @throws IOException
     */
    public void close() throws IOException {
        autoSaveThread.interrupt();
        fileWriter.close();
    }

    /**
     * 手动保存
     * @throws IOException
     */
    public void save() throws IOException {
        if (!changed) {
            return;
        }
        System.out.println("execute the save action");
        for (String cacheLine : content) {
            fileWriter.write(cacheLine);
            fileWriter.write("\r\n");
        }
        fileWriter.flush();
        changed = false;
        content.clear();
    }
}