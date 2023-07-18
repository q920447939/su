/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年04月03日
 */
package cn.withmes.su.server.business.handler.inbound.chat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.NetUtil;
import cn.withemes.common.protocol.ChannelResponseWriteDecorator;
import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.chat.ChatRequest;
import cn.withmes.su.server.business.entity.response.ChatResponse;
import cn.withmes.su.server.business.enums.response.chat.SingleChatResponseEnums;
import cn.withmes.su.server.business.event.entity.UserChannelRelation;
import cn.withmes.su.server.business.utils.channel.Session;
import cn.withmes.su.server.business.utils.channel.SessionWrapper;
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
    private SessionWrapper sessionWrapper;


    @Resource
    private ChannelResponseWriteDecorator channelResponseWriteDecorator;



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatRequest chatRequest = (ChatRequest) msg;
        Session session = SessionWrapper.getSession(ctx.channel());
        Long userId = session.getUserId();
        String userName = session.getUserName();
        Set<UserChannelRelation> userSessionAllMachine = sessionWrapper.getUserSessionAllMachine(chatRequest.getToUid());
        if (CollUtil.isEmpty(userSessionAllMachine)){
            log.warn("userSessionAllMachine is empty");
            return;
        }
        String sendMessage = chatRequest.getMessage();
        for (UserChannelRelation relation : userSessionAllMachine) {
            //获取到redis 缓存中，所有接收者的channel集合，当集合id在本机器节点上找到，说明接收者在此机器上登录过
            Channel channel = sessionWrapper.getChannelByChannelId(relation.getChannelId());
            if (null != channel){
                //当前机器
                Session toSession = SessionWrapper.getSession(channel);
                String message = JSONObject.toJSONString(ChatResponse.builder().toUid(userId).toNickName(userName).toUid(toSession.getUserId()).toNickName(toSession.getUserName()).message(sendMessage).build());
                channelResponseWriteDecorator.writeAndFlush(channel,ResponseWrapper.chatResponse(SingleChatResponseEnums.CHAT_MESSAGE_SUCCESS,message));
                continue;
            }
            //到这里 ，有可能是不属于这个节点，也有可能是 这个channel 已经 断开连接了，但是存在redis里面的映射关系还没有做移除

            //其他节点 TODO 考虑用一个模块处理，因为如果使用广播的模式，当节点数量过多，那么每个节点都需要出发这个循环
            //优雅的做法应该是，找到具体的那个应用。直接通知那台机器需要给 “接收者” 响应消息。
            //此时，还需要做离线，掉线处理 场景
            /*Session toSession = SessionUtil.getSession(relation.getIp());
            if (null == toSession){
                log.warn("获取接收用户信息为空，可能是客户端已主动断开连接过");
                continue;
            }*/
        }
    }
}
