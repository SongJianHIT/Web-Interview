/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demo1
 * @className tech.songjian.netty.demo1.NettyClient
 */
package tech.songjian.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * NettyClient
 * @description 客户端
 * @author SongJian
 * @date 2023/6/8 10:28
 * @version
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        // 设置线程组
        bootstrap.group(group)
                // 设置客户端的 channel 实现类
                .channel(NioSocketChannel.class)
                // 创建一个 channel 初始化对象
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 往 pipline 链中添加自定义 handler
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        System.out.println("-------客户端 准备就绪 发射 msg---------");
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 9999).sync();
        // 关闭连接
        cf.channel().closeFuture().sync();
    }
}

