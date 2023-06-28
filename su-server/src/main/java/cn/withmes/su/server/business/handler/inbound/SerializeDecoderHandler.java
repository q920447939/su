/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.handler.inbound;

import cn.withmes.su.server.business.pack.Package;
import cn.withmes.su.server.business.strategy.pack.PackageStrategyDecorate;
import cn.withmes.su.server.business.strategy.serialize.SerializeStrategy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClassName: SerializeDecoderHandler
 * @Description: 序列化处理器
 * @author leegoo
 * @date 2023年06月25日
 */
@Service
@Slf4j
@ChannelHandler.Sharable
public class SerializeDecoderHandler extends ChannelInboundHandlerAdapter {
    @Resource
    private SerializeStrategy serializeStrategy;
    @Resource
    private PackageStrategyDecorate packageStrategyDecorate;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将字节数组转换为ByteBuf
        Package pack = serializeStrategy.dec((byte[]) msg);
        super.channelRead(ctx, pack);
    }

}
