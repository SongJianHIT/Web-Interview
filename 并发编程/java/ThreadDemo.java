/**
 * @projectName Web-Interview
 * @package PACKAGE_NAME
 * @className PACKAGE_NAME.ThreadDemo
 */

/**
 * ThreadDemo
 * @description 测试线程
 * @author SongJian
 * @date 2023/6/6 15:29
 * @version
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.printf("我是一个线程！");
            }
        };
        Thread thread = new Thread(r);
        // 为什么调用 start 方法，也就执行了 run 方法？
        thread.start();
    }
}

