/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demo1
 * @className tech.songjian.netty.demo1.NettyClientHandler
 */
package tech.songjian.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * NettyClientHandler
 * @description 客户端处理类
 * @author SongJian
 * @date 2023/6/8 10:33
 * @version
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf byteBuf = Unpooled.copiedBuffer("天王盖地虎", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    /**
     * 读取数据事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("server msg: " + byteBuf.toString(CharsetUtil.UTF_8));
    }
}

