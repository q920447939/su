/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月17日
 */
package cn.withemes.common.protocol;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

/**
 * ClassName: ChannelResponseWriteDecorator
 * @Description:
 * @author leegoo
 * @date 2023年07月17日
 */
@Service
public class ChannelResponseWriteDecorator {
    public void writeAndFlush(ChannelHandlerContext ctx, Object msg) {
        ctx.writeAndFlush(BytBufUtils.objToByteBuf(msg));
    }
}
