/**
 * @projectName Web-Interview
 * @package chat
 * @className chat.ChatServer
 */
package chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * ChatServer
 * @description 聊天服务端
 * @author SongJian
 * @date 2023/6/7 23:49
 * @version
 */
public class ChatServer {

    /**
     * 服务端监听通道
     */
    private ServerSocketChannel listenerChannel;

    /**
     * 服务端的选择器对象
     */
    private Selector selector;

    /**
     * 服务端端口
     */
    private static final int PORT = 9999;

    public static void main(String[] args) throws Exception {
        new ChatServer().start();
    }

    /**
     * 构造器，初始化
     */
    public ChatServer() {
        try {
            // 1、开启 Socket 监听通道
            listenerChannel = ServerSocketChannel.open();
            // 2、开启 selector
            selector = Selector.open();
            // 3、绑定端口
            listenerChannel.bind(new InetSocketAddress(PORT));
            // 4、设置非阻塞
            listenerChannel.configureBlocking(false);
            // 5、将 selector 绑定到监听 accept 事件
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
            printInfo("见见聊天室 启动");
            printInfo("见见聊天室 初始化端口:" + PORT);
            printInfo("见见聊天室 初始化IP地址：" + listenerChannel.getLocalAddress());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 服务端启动
     */
    private void start() throws Exception {
        try {
            while (true) {
                // 不停地监控
                if (selector.select(2000) == 0) {
                    System.out.println("没有客户端连接，等待中。。。");
                    continue;
                }
                // 接收到建立连接请求后，才会执行下面
                // 遍历注册关系
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        // 如果是一个连接请求的事件，则建立连接
                        SocketChannel sc = listenerChannel.accept();
                        // 设置非阻塞
                        sc.configureBlocking(false);
                        // 注册读事件的监听
                        sc.register(selector, SelectionKey.OP_READ);
                        System.out.println(sc.getRemoteAddress().toString().substring(1) + "上线了！");
                    }
                    if (key.isReadable()) {
                        // 如果是一个读事件
                        // 服务端会读取数据，并且进行数据广播！
                        readMsg(key);
                    }
                    // 一定要把当前的 key 删除，防止反复处理
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据，并且进行数据广播
     * @param key
     */
    private void readMsg(SelectionKey key) throws Exception {
        // 获取 SelectionKey 对应的 channel
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int count = 0;
        try {
            count = channel.read(buf);
        } catch (IOException e) {
            System.out.println(channel.getRemoteAddress().toString().substring(1) + "已经下线！");
            channel.close();
        }
        if (count > 0) {
            String msg = new String(buf.array());
            // 打印消息
            printInfo(msg);
            // 广播
            broadCast(channel, msg);
        }
    }

    /**
     * 广播消息
     * @param sourceC
     * @param msg
     */
    private void broadCast(SocketChannel sourceC, String msg) throws Exception {
        System.out.println("服务器广播了消息！");
        for (SelectionKey key : selector.keys()) {
            // selector 中包含了 SocketChannel 和 ServerSocketChannel
            // 因此需要判断一下类型，只向 SocketChannel 广播
            Channel targetC = key.channel();
            if (targetC instanceof SocketChannel && targetC != sourceC) {
                SocketChannel destC = (SocketChannel) targetC;
                ByteBuffer buf = ByteBuffer.wrap(msg.getBytes());
                destC.write(buf);
            }
        }
    }

    /**
     * 打印消息
     * @param msg
     */
    private void printInfo(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + sdf.format(new Date()) + "]" + msg);
    }
}

