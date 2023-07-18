/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年04月03日
 */
package cn.withmes.su.server.business.handler.inbound.chat;

import cn.hutool.extra.spring.SpringUtil;
import cn.withemes.common.protocol.ChannelResponseWriteDecorator;
import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.chat.ChatRequest;
import cn.withmes.su.server.business.enums.ChatEnums;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.response.chat.SingleChatResponseEnums;
import cn.withmes.su.server.business.pack.Package;
import cn.withmes.su.server.business.utils.LoginUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@ChannelHandler.Sharable
@Slf4j
public class ChatChannelHandle extends ChannelInboundHandlerAdapter {

    @Resource
    private ChannelResponseWriteDecorator channelResponseWriteDecorator;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Package packget)) {
            return;
        }
        if (PackageEnums.CHAT.getType() != packget.getCommand()) {
            super.channelRead(ctx, msg);
            return;
        }
        Channel channel = ctx.channel();
        if (!LoginUtil.isLogin(channel)) {
            log.warn("未登陆");
            channelResponseWriteDecorator.writeAndFlush(ctx, ResponseWrapper.loginFailResponse(SingleChatResponseEnums.UN_LOGIN));
            return;
        }
        ChatRequest chatRequest = JSONObject.toJavaObject((JSONObject) packget.getBody(), ChatRequest.class);
        if (null == chatRequest || null == chatRequest.getChatType() || null == ChatEnums.getEnumsByType(chatRequest.getChatType())) {
            log.warn("未获取chat 枚举类型");
            super.channelRead(ctx, msg);
            return;
        }
        ChannelInboundHandlerAdapter adapter = SpringUtil.getBean(ChatEnums.getEnumsByType(chatRequest.getChatType()).getClz());
        adapter.channelRead(ctx, chatRequest);
    }
}
