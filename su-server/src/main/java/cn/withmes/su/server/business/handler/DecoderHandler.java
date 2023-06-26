/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.handler;

import cn.withmes.su.server.business.config.ChatServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DecoderHandler
 *
 * @author leegoo
 * @Description:解码器处理
 * @date 2023年06月25日
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class DecoderHandler extends ByteToMessageDecoder {
    @Resource
    private ChatServerConfig chatServerConfig;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            Object r = this.decode0(channelHandlerContext, byteBuf);
            if (null == r){
                log.warn("解码失败");
                channelHandlerContext.close();
                return;
            }
            list.add(r);
        } catch (Exception e) {
            log.error("解码失败", e);
            channelHandlerContext.close();
        }
    }


    private Object decode0(ChannelHandlerContext ctx, ByteBuf in) throws  Exception{
        if (!in.isReadable()) {
            log.warn("is  unreadable");
            return null;
        }
        if (in.readableBytes() < 8 ){
            log.warn("不够8字节");
            return null;
        }
        //读取魔数 2字节
        short magic = in.readShort();
        if (magic != chatServerConfig.getMagic()) {
            log.warn("魔数不正确");
            return null;
        }
        //读取版本 2个字节
        short version = in.readShort();
        if (version <= 0 ) {
            log.warn("版本不正确");
            return null;
        }
        //读取长度 ，长度不够判断
        int length = in.readInt();
        if (length <= 0) {
            log.warn("长度不正确");
            return null;
        }
        //读取内容
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        return bytes;
    }
}
