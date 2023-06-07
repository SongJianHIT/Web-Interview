/**
 * @projectName Web-Interview
 * @package PACKAGE_NAME
 * @className PACKAGE_NAME.TestNIO
 */

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TestNIO
 * @description
 * @author SongJian
 * @date 2023/6/7 22:19
 * @version
 */
public class TestNIO {

    @Test
    /**
     * 使用 NIO 往本地文件中写数据
     */
    public void test1() throws Exception {
        // 1、创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./basic.txt");
        // 2、获取流的一个 channel
        FileChannel channel = fileOutputStream.getChannel();
        // 3、创建一个字节缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 4、输入的数据
        String s = "Hello NIO";
        byteBuffer.put(s.getBytes());
        // 5、翻转缓冲区
        byteBuffer.flip();
        // 6、把缓冲区写入通道中
        channel.write(byteBuffer);
        // 7、关闭流
        fileOutputStream.close();
    }
    @Test
    /**
     * 使用 NIO 从本地文件中读数据
     */
    public void test2() throws Exception {
        // 1、创建输入流
        FileInputStream fileInputStream = new FileInputStream("basic.txt");
        // 2、得到一个 channel
        FileChannel channel = fileInputStream.getChannel();
        // 3、准备一个 buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4、从 channel 中读字节数组
        channel.read(buffer);
        String s = new String(buffer.array());
        System.out.println(s);
        // 5、关闭流
        fileInputStream.close();
    }

    /**
     * 使用 NIO 进行文件复制
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        // 1、创建两个流
        FileInputStream fileInputStream = new FileInputStream("basic.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("new_basic.txt");

        // 2、得到他们的 channel
        FileChannel sourceC = fileInputStream.getChannel();
        FileChannel targetC = fileOutputStream.getChannel();

        // 3、复制
        targetC.transferFrom(sourceC, 0, sourceC.size());

        // 4、关闭
        fileInputStream.close();
        fileOutputStream.close();
    }
}

