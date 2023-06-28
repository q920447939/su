package cn.withmes.su.server.business.handler.inbound;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Service;

import java.io.IOException;

@ChannelHandler.Sharable
@Service
public class GlobalExceptionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException && "Connection reset".equals(cause.getMessage())) {
            // 处理连接重置异常
            // 这里可以记录日志、关闭连接等操作
            // 例如：
            ctx.close();
            // 或者：
             //logger.error("Connection reset", cause);
        } else {
            // 其他异常情况，调用父类方法进行默认处理
            super.exceptionCaught(ctx, cause);
        }
    }
}
