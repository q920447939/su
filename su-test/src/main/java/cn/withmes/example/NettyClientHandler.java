package cn.withmes.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 连接建立的完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeShort(0x410F);
        buffer.writeShort(1);
        byte[] bytes = "{\"body\":{\"password\":\"123\",\"userName\":\"sd\"},\"command\":1}".getBytes();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        ctx.channel().writeAndFlush(buffer);
    }

    /**
     * 读取数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf responseByteBuf= (ByteBuf)msg;
        byte[] responseByte=new byte[responseByteBuf.readableBytes()];
        responseByteBuf.readBytes(responseByte);
        System.out.println(new String(responseByte));
    }


    /**
     * 异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
