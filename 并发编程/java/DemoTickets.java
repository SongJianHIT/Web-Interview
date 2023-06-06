/**
* @projectName Web-Interview
* @package PACKAGE_NAME
* @className PACKAGE_NAME.DemoTickets
*/
/**
* DemoTickets
* @description
* @author SongJian
* @date 2023/6/6 16:25
* @version
*/
public class DemoTickets {
    public static void main(String[] args) {
        SellTickets sellTickets = new SellTickets();

        Thread t1 = new Thread(sellTickets, "窗口1");
        Thread t2 = new Thread(sellTickets, "窗口2");
        Thread t3 = new Thread(sellTickets, "窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class SellTickets implements Runnable {

    // 一共 100 张票
    private int tickets = 100;
    private final Integer lock = 1;

    @Override
    public void run() {
        while (true) {
            // 同步代码块
            synchronized (lock) {
                if (tickets > 0) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //获取当前线程对象的名字
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "-正在卖:" + tickets--);
                }
            }
        }
    }
}

