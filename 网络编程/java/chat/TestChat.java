/**
 * @projectName Web-Interview
 * @package chat
 * @className chat.TestChat
 */
package chat;

import java.util.Scanner;

/**
 * TestChat
 * @description 测试类
 * @author SongJian
 * @date 2023/6/8 00:28
 * @version
 */
public class TestChat {
    public static void main(String[] args) throws Exception {
        ChatClient chatClient = new ChatClient();

        /**
         * 开一个单独的自线程，来监听服务器发过来的消息
         */
        new Thread(()->{
            // 监听服务器信息
            while (true) {
                try {
                    Thread.sleep(2000);
                    chatClient.receiveMsg();
                } catch (Exception e) {
                    System.out.println("请检查服务器是否关闭～");
                }
            }
        }).start();

        /**
         * 发送消息
         */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}

