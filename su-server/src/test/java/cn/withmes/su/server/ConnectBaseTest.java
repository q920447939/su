/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server;

import cn.hutool.core.collection.CollUtil;
import cn.withmes.su.server.business.config.ChatServerConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

/**
 * ClassName: ConnectBaseTest
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月26日
 */
@Slf4j
public abstract class ConnectBaseTest extends SpringBootTestEnvBaseTest {
    @Resource
    protected ChatServerConfig chatServerConfig;
    protected Channel channel;

    private static final Bootstrap bootstrap = new Bootstrap();
    EventLoopGroup loopGroup = new NioEventLoopGroup();

    @BeforeEach
    public void connection() {
        bootstrap.group(loopGroup);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                List<ChannelHandler> pipeList = addLast();
                if (CollUtil.isNotEmpty(pipeList)) {
                    pipeline.addLast(pipeList.toArray(new ChannelHandler[0]));
                }
            }
        });
        bootstrap.remoteAddress("127.0.0.1", chatServerConfig.getPort());
        ChannelFuture channelFuture = bootstrap.connect();
        channelFuture.addListener(callback(channelFuture));
        while (!channelFuture.isSuccess()) {
            channelFuture.awaitUninterruptibly();
        }
    }

    @After
    public void close() {
        loopGroup.shutdownGracefully();
    }

    protected List<ChannelHandler> addLast() {
        return null;
    }

    protected GenericFutureListener<? extends Future<? super Void>> callback(ChannelFuture channelFuture) {
        this.channel = channelFuture.channel();
        return (ChannelFutureListener) future -> {
            Channel channel = future.channel();
            if (!future.isSuccess()) {
                log.info("client 连接未成功");
                return;
            }
            log.info("client 连接成功 。channel={}", channel);
            callback1();
        };
    }


    protected abstract void callback1();
}
