/**
 * @projectName Web-Interview
 * @package tech.songjian.netty.demoGcoderc
 * @className tech.songjian.netty.demoGcoderc.NettyEncoderDecoderServerHandler
 */
package tech.songjian.netty.demoGcoderc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * NettyEncoderDecoderServerHandler
 * @description
 * @author SongJian
 * @date 2023/6/8 11:28
 * @version
 */
public class NettyEncoderDecoderServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BookMessage.Book book = (BookMessage.Book) msg;
        System.out.println(book.getName());
    }
}

