/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.stereotype.Service;

/**
 * 分裂译码器处理人
 * ClassName: SplitDecoderHandler
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Service
public class SplitDecoderHandler extends LengthFieldBasedFrameDecoder {

    public SplitDecoderHandler() {
        super(65535, 4, 4,0,0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return super.decode(ctx, in);
    }
}
