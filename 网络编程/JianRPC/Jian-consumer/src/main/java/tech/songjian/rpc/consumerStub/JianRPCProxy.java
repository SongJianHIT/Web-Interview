/**
 * @projectName Web-Interview
 * @package tech.songjian.rpc.consumerStub
 * @className tech.songjian.rpc.consumerStub.JianRPCProxy
 */
package tech.songjian.rpc.consumerStub;

import com.google.common.reflect.ClassPath;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import tech.songjian.rpc.producerStub.ClassInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JianRPCProxy
 * @description 服务代理对象
 * @author SongJian
 * @date 2023/6/8 15:22
 * @version
 */
public class JianRPCProxy {

    /**
     * 根据接口，创建代理对象
     * @param target
     * @return
     */
    public static Object create(Class target) {
        // 获取目标对象的类加载
        ClassLoader classLoader = target.getClassLoader();
        Class[] interfaces = {target};

        InvocationHandler invocationHandler = new InvocationHandler() {
            // 所有代理对象的方法的执行都会进来
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 封装一个 ClassInfo
                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassName(target.getName());
                classInfo.setMethodName(method.getName());
                classInfo.setObjects(args);
                classInfo.setTypes(method.getParameterTypes());

                // 利用 Netty 发送数据
                NioEventLoopGroup group = new NioEventLoopGroup();
                ResultHandler resultHandler = new ResultHandler();
                try {
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    ChannelPipeline pipeline = socketChannel.pipeline();
                                    pipeline.addLast("encoder", new ObjectEncoder());
                                    pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                    pipeline.addLast("handler", resultHandler);
                                }
                            });
                    ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();
                    future.channel().writeAndFlush(classInfo).sync();
                    future.channel().closeFuture().sync();
                } finally {
                    group.shutdownGracefully();
                }
                return resultHandler.getResponse();
            }
        };
        // 创建一个代理对象并返回
        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}

