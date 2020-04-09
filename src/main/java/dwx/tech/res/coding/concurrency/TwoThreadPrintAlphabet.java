package dwx.tech.res.coding.concurrency;

/**
 * 题目：如何控制两个线程按照字母表顺序交叉输出大小写
 *
 * @author dwx
 */
public class TwoThreadPrintAlphabet {

    /**
     * 等待通知同步器
     */
    public static Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            synchronized (lock) {
                int i = 0;
                /**
                 * 自旋
                 */
                while (i < 26) {
                    int r = 'a' + i;
                    System.out.print((char)r);
                    i++;
                    try {
                        if (i <= 25) {
                            lock.notify();
                            lock.wait();
                        } else {
                            lock.notify();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, "thread t1");

        Thread t2 = new Thread(()->{
            synchronized (lock) {
                int i = 0;
                while (i < 26) {
                    int r = 'A' + i;
                    System.out.print((char)r);
                    i++;
                    try {
                        if (i < 26) {
                            lock.notify();
                            lock.wait();
                        } else {
                            System.out.println("\nprint over!");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, "thread t2");

        t1.start();
        t2.start();
    }
}
