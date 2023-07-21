package cn.withmes.su.server.business.handler.state;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@ChannelHandler.Sharable
@Slf4j
public class ServerIdleStateHandler extends IdleStateHandler {

    //300秒
    private static final int READER_IDLE_TIME = 60*5;

    //如果300秒内没有读到数据，就表示连接假死
    public ServerIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info("{}秒内未读到数据，关闭连接",READER_IDLE_TIME);
        ctx.channel().close();
    }
}
