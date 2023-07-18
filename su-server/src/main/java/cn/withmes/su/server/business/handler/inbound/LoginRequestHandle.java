/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月28日
 */
package cn.withmes.su.server.business.handler.inbound;

import cn.hutool.extra.spring.SpringUtil;
import cn.withemes.common.protocol.BytBufUtils;
import cn.withemes.common.protocol.ChannelResponseWriteDecorator;
import cn.withemes.user.api.dto.UserDTO;
import cn.withemes.user.api.dto.UserQueryRequestDTO;
import cn.withemes.user.api.facade.UserInfoFacade;
import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.login.LoginRequest;
import cn.withmes.su.server.business.entity.login.evnet.LoginSuccEventInfo;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.response.login.LoginResponseEnums;
import cn.withmes.su.server.business.handler.inbound.chat.ChatChannelHandle;
import cn.withmes.su.server.business.handler.inbound.userlist.UserChannelHandle;
import cn.withmes.su.server.business.pack.Package;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * ClassName: LoginRequest
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月28日
 */
@Service
@ChannelHandler.Sharable
@Scope("prototype")
@Slf4j
public class LoginRequestHandle extends ChannelInboundHandlerAdapter {
    @DubboReference
    private UserInfoFacade userInfoFacade;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private ChannelResponseWriteDecorator channelResponseWriteDecorator;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("收到一个新连接，但是还没有登录 {}",ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Package packget)) {
            return;
        }
        if (PackageEnums.LOGIN.getType() != packget.getCommand()) {
            channelResponseWriteDecorator.writeAndFlush(ctx, ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_COMMAND_IS_ERROR));
            return;
        }
        LoginRequest loginRequest = JSONObject.toJavaObject((JSONObject) packget.getBody(), LoginRequest.class);
        UserDTO user = userInfoFacade.getUserInfo(UserQueryRequestDTO.builder().username(loginRequest.getUserName()).password(loginRequest.getPassword()).build());
        if (null == user) {
            channelResponseWriteDecorator.writeAndFlush(ctx, ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_FAIL_USERNAME_OR_PASSWORD_ERROR));
            return;
        }
        applicationContext.publishEvent(LoginSuccEventInfo.builder().user(user).channel(ctx.channel()).build());
        channelResponseWriteDecorator.writeAndFlush(ctx,ResponseWrapper.loginSuccResponse(LoginResponseEnums.LOGIN_SUCC));
        ctx.pipeline().remove(this);
        ctx.pipeline().addLast("chat", SpringUtil.getBean(ChatChannelHandle.class));
        ctx.pipeline().addLast("userlist", SpringUtil.getBean(UserChannelHandle.class));
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved");
        super.handlerRemoved(ctx);
        //log.info("无登录验证，强制关闭连接！");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive");
        super.channelInactive(ctx);
    }
}
