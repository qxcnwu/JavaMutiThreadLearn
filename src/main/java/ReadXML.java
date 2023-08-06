import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Book implements Serializable {
    private String title;
    private String author;
    private String price;

    public String getTitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setauthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}

class BookId implements Serializable{
    private Book book;
    private String id;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BookId{" +
                "book=" + book +
                ", id=" + id +
                '}';
    }
}

public class ReadXML {

    public static List<BookId> readXML(String path){
        List<BookId> answer=new ArrayList<>();
        SAXReader saxReader=new SAXReader();
        try{
            Document doc= saxReader.read(path);
            Element books=doc.getRootElement();
            Iterator<Element> iterator=books.elementIterator();
            while(iterator.hasNext()){
                Element book= iterator.next();
                BookId bookid=new BookId();
                book.attributes().forEach(attr->{
                    System.out.println();
//                        Method method = BookId.class.getDeclaredMethod("set"+attr.getName(),String.class);
//                        method.invoke(bookid,attr.getValue());
                });
                Iterator<Element> itt = book.elementIterator();
                Book bookIn=new Book();
                itt.forEachRemaining(ele->{
                    Method method = null;
                    try {
                        method = Book.class.getDeclaredMethod("set" + ele.getName(),String.class);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert method != null;
                        method.invoke(bookIn,ele.getStringValue());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
                bookid.setBook(bookIn);
                answer.add(bookid);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return answer;
    }

    public static void main(String[] args) {
        String path="D:\\JAVA\\Th\\src\\main\\resources\\book.xml";
        final List<BookId> bookIds = readXML(path);
        bookIds.forEach(System.out::println);
    }
}