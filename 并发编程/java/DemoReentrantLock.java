/**
 * @projectName Web-Interview
 * @package PACKAGE_NAME
 * @className PACKAGE_NAME.DemoReentrantLock
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DemoReentrantLock
 * @description
 * @author SongJian
 * @date 2023/6/7 10:16
 * @version
 */
public class DemoReentrantLock {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);
        lock.lock();
        try {
            // 业务代码
        } finally {
            lock.unlock();
        }
    }
}

