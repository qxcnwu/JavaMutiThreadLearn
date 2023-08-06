package singlethreadexec;

import java.util.Arrays;

/**
 * @author 邱星晨
 * 应当在线程安全的情况下减少synchronized的作用域
 */
public class EatNoodles {
    /**
     * 模拟两个锁互斥互相解锁的情况
     */
    public static void main(String[] args) {
        TableWare tableWare1 = new TableWare("1");
        TableWare tableWare2 = new TableWare("2");
        Eatthread eatthread1 = new Eatthread(tableWare1, tableWare2);
        Eatthread eatthread2 = new Eatthread(tableWare2, tableWare1);
        eatthread1.start();
        eatthread2.start();
    }
}

class TableWare {
    private final String name;

    public TableWare(String str) {
        this.name = str;
    }

    @Override
    public String toString() {
        return "TableWare{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Eatthread extends Thread {

    private TableWare left;
    private TableWare right;

    public Eatthread(TableWare left, TableWare right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            this.eat();
        }
    }

    private void eat() {
        synchronized (left) {
            System.out.println(left);
            synchronized (right) {
                System.out.println(right);
                System.out.println("eat");
                System.out.println(right);
            }
            System.out.println("eat");
        }
    }
}