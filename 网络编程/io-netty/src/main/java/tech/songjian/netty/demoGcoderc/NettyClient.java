/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demo1
 * @className tech.songjian.netty.demo1.NettyClient
 */
package tech.songjian.netty.demoGcoderc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import tech.songjian.netty.demo1.NettyClientHandler;

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
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 添加一个编码器、解码器
                        socketChannel.pipeline().addLast("encoder", new ProtobufEncoder());
                        socketChannel.pipeline().addLast(new NettyEncoderDecoderClientHandler());
                    }
                });
        System.out.println("-------客户端 准备就绪 发射 msg---------");
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 9999).sync();
        // 关闭连接
        cf.channel().closeFuture().sync();
    }
}

