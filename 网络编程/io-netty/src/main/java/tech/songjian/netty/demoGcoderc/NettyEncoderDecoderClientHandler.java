/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demoGcoderc
 * @className tech.songjian.netty.demoGcoderc.NettyEncoderDecoderClientHandler
 */
package tech.songjian.netty.demoGcoderc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * NettyEncoderDecoderClientHandler
 * @description
 * @author SongJian
 * @date 2023/6/8 11:21
 * @version
 */
public class NettyEncoderDecoderClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 构造者模式
        BookMessage.Book book = BookMessage.Book.newBuilder().setId(1).setName("天王盖地虎").build();
        ctx.writeAndFlush(book);
    }
}

