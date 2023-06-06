/**
 * @projectName Web-Interview
 * @package PACKAGE_NAME
 * @className PACKAGE_NAME.VolatileDemo
 */

/**
 * VolatileDemo
 * @description
 * @author SongJian
 * @date 2023/6/6 22:26
 * @version
 */
public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        DemoV demoV = new DemoV();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(demoV);
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println(demoV.count);
    }
}

class DemoV implements Runnable {

    public volatile int count = 0;

    @Override
    public void run() {

        addCount();
    }

    private void addCount() {
        for (int i = 0; i < 10000; ++i) {
            // count++ 在汇编层面上是三条指令，并非原子性操作
            count++;
        }
    }
}

