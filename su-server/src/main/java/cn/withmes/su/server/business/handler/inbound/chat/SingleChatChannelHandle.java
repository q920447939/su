/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年04月03日
 */
package cn.withmes.su.server.business.handler.inbound.chat;

import cn.hutool.core.collection.CollUtil;
import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.chat.ChatRequest;
import cn.withmes.su.server.business.entity.response.ChatResponse;
import cn.withmes.su.server.business.enums.response.chat.SingleChatResponseEnums;
import cn.withmes.su.server.business.enums.response.login.LoginResponseEnums;
import cn.withmes.su.server.business.utils.channel.Session;
import cn.withmes.su.server.business.utils.channel.SessionUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@ChannelHandler.Sharable
@Slf4j
public class SingleChatChannelHandle extends ChannelInboundHandlerAdapter {
    @Resource
    private SessionUtil sessionUtil;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatRequest chatRequest = (ChatRequest) msg;
        Session session = SessionUtil.getSession(ctx.channel());
        Long userId = session.getUserId();
        String userName = session.getUserName();
        Set<Channel> toUserIdChannelSet = sessionUtil.getUserIdChannelMap(chatRequest.getToUid());
        if (CollUtil.isEmpty(toUserIdChannelSet)) {
            log.debug("toUserIdChannelSet is empty");
            ctx.writeAndFlush(ResponseWrapper.chatResponse(SingleChatResponseEnums.CHAT_TO_USER_OFFLINE,"用户不在线"));
            super.channelRead(ctx, msg);
            return;
        }
        for (Channel channel : toUserIdChannelSet) {
            Session toSession = SessionUtil.getSession(channel);
            String message = JSONObject.toJSONString(ChatResponse.builder().toUid(userId).toNickName(userName).toUid(toSession.getUserId()).toNickName(toSession.getUserName()).build());
            channel.writeAndFlush(ResponseWrapper.chatResponse(SingleChatResponseEnums.CHAT_MESSAGE_SUCCESS,message));
        }
        ctx.writeAndFlush(ResponseWrapper.chatResponse(SingleChatResponseEnums.CHAT_MESSAGE_SUCCESS1,""));
        super.channelRead(ctx, msg);
    }
}
