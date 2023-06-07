/**
 * @projectName Web-Interview
 * @package chat
 * @className chat.ChatClient
 */
package chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ChatClient
 * @description 聊天客户端
 * @author SongJian
 * @date 2023/6/8 00:23
 * @version
 */
public class ChatClient {
    /**
     * 服务器地址
     */
    private final String HOST = "127.0.0.1";

    /**
     * 服务器端口
     */
    private int PORT = 9999;

    /**
     * 网络通道
     */
    private SocketChannel socketChannel;

    /**
     * 聊天用户名
     */
    private String userName;

    public ChatClient() throws Exception {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress add = new InetSocketAddress(HOST, PORT);
        if (!socketChannel.connect(add)) {
            while (!socketChannel.finishConnect()) {
                // NIO 作为非阻塞式的优势
                System.out.println("还没连接上，可以干别的事！");
            }
        }
        userName = "aa";
        System.out.println("---------------Client(" + userName + ") is ready-----------------");
    }

    /**
     * 接收消息
     */
    public void receiveMsg() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        if (!socketChannel.isOpen()) {
            return;
        }
        int size = socketChannel.read(buf);
        if (size > 0) {
            String msg = new String(buf.array());
            System.out.println(msg.trim());
        }
    }

    /**
     * 核心方法，发送消息
     * @param msg
     * @throws Exception
     */
    public void sendMsg(String msg) throws Exception {
        /**
         * 断开连接
         */
        if (msg.equalsIgnoreCase("bye")) {
            socketChannel.close();
            return;
        }
        msg = userName + "说：" + msg;
        ByteBuffer buf = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buf);
    }
}

