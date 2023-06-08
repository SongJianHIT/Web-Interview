/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demo1
 * @className tech.songjian.netty.demo1.NettyServerHandler
 */
package tech.songjian.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * NettyServerHandler
 * @description 服务端处理类
 * @author SongJian
 * @date 2023/6/8 10:10
 * @version
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取事件
     * @param ctx
     * @param msg 用户发过来的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 从缓冲区接收数据
        ByteBuf byteBuf = (ByteBuf) msg;
        // 编码、解码
        System.out.println("client msg: "+ ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
    }

    /**
     * 读取数据完毕事件
     * 读取完客户端数据后回复客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("宝塔镇河妖", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    /**
     * 异常发生事件
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 异常时关闭 ctx
        ctx.close();
    }
}

