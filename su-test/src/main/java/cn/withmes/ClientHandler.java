package cn.withmes;
// 在ChannelHandler中 handling server responses

import com.alibaba.fastjson2.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf responseByteBuf= (ByteBuf)msg;
        byte[] responseByte=new byte[responseByteBuf.readableBytes()];
        responseByteBuf.readBytes(responseByte);
        System.out.println(new String(responseByte));
    }

}


// 响应对象

@Data
class LoginResponse {

    private int code;

    private String msg;

}

@Data
class ChatResponse {

    private boolean success;

}
