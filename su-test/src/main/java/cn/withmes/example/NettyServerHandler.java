package cn.withmes.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf= (ByteBuf)msg;
        byte[] body=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(body);
        System.out.println(new String(body));
        byte[] responseBytes = "hi,客户端,我是服务端".getBytes();
        ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(responseBytes));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
