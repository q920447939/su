/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business;

import cn.withmes.su.server.business.config.ChatServerConfig;
import cn.withmes.su.server.business.handler.inbound.DecoderHandler;
import cn.withmes.su.server.business.handler.inbound.GlobalExceptionHandler;
import cn.withmes.su.server.business.handler.inbound.LifeCycleChanelInboundHandle;
import cn.withmes.su.server.business.handler.inbound.LoginRequestHandle;
import cn.withmes.su.server.business.handler.inbound.SerializeDecoderHandler;
import cn.withmes.su.server.business.handler.inbound.SplitDecoderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * ClassName: ChatServer
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月25日
 */
@Service
@Slf4j
public class ChatServer {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private ChatServerConfig chatServerConfig;
    @Resource
    private SerializeDecoderHandler serialize;
    @Resource
    private LifeCycleChanelInboundHandle lifeCycleChanelInboundHandle;

    @Resource
    private GlobalExceptionHandler globalExceptionHandler;

    private final ServerBootstrap bootstrap = new ServerBootstrap();


    public void start() {
        NioEventLoopGroup accept = new NioEventLoopGroup();
        NioEventLoopGroup handle = new NioEventLoopGroup();
        try {
            bootstrap.group(accept, handle);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.localAddress(chatServerConfig.getPort());
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addFirst("lifeCycleChanelInboundHandle",lifeCycleChanelInboundHandle);
                    pipeline.addLast("splitDecoderHandler",applicationContext.getBean(SplitDecoderHandler.class));
                    pipeline.addLast("globalExceptionHandler",globalExceptionHandler);
                    pipeline.addLast("decoder", applicationContext.getBean(DecoderHandler.class));
                    pipeline.addLast("loginRequestHandle", applicationContext.getBean(LoginRequestHandle.class));
                    pipeline.addAfter("decoder","serialize",serialize);
                }
            });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            log.info("服务端启动成功，绑定端口:{}", chatServerConfig.getPort());
            ChannelFuture future = channelFuture.channel().closeFuture().sync();
            if (future.isSuccess()) {
                log.info("服务端关闭成功！");
            }
        } catch (InterruptedException e) {
            log.error("服务端启动失败", e);
        } finally {
            accept.shutdownGracefully();
            handle.shutdownGracefully();
        }
    }
}
