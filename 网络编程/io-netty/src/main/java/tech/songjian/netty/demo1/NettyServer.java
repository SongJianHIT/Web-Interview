/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demo1
 * @className tech.songjian.netty.demo1.NettyServer
 */
package tech.songjian.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * NettyServer
 * @description 服务端
 * @author SongJian
 * @date 2023/6/8 09:58
 * @version
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // bossGroup：接收客户端连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // workerGroup：处理网络业务操作
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 启动助手，设置启动参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 设置两个线程组
        serverBootstrap.group(bossGroup, workerGroup)
                // 使用 NioServerSocketChannel 作为服务器端channel实现
                .channel(NioServerSocketChannel.class)
                // 设置线程队列中等待连接的个数
                .option(ChannelOption.SO_BACKLOG, 128)
                // 设置长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 创建一个channel初始化对象，往Pipline链中添加自定义的 Handler 类
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("-----服务端 启动中 init port:9999-------");
        ChannelFuture cf = serverBootstrap.bind(9999).sync();
        System.out.println("------------服务端 启动成功-------------");

        // 关闭通道
        cf.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

