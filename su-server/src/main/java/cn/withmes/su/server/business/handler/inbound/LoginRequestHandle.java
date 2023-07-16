/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月28日
 */
package cn.withmes.su.server.business.handler.inbound;

import cn.withemes.user.api.dto.UserDTO;
import cn.withemes.user.api.dto.UserQueryRequestDTO;
import cn.withemes.user.api.facade.UserInfoFacade;
import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.login.LoginRequest;
import cn.withmes.su.server.business.entity.login.evnet.LoginSuccEventInfo;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.response.login.LoginResponseEnums;
import cn.withmes.su.server.business.pack.Package;
import cn.withmes.su.server.business.utils.LoginUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class LoginRequestHandle extends SimpleChannelInboundHandler<Package> {
    @DubboReference
    private UserInfoFacade userInfoFacade;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Package msg) throws Exception {
        if (PackageEnums.LOGIN.getType() != msg.getCommand()) {
            ctx.writeAndFlush(ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_COMMAND_IS_ERROR));
            return;
        }
        LoginRequest loginRequest = JSONObject.toJavaObject((JSONObject) msg.getBody(), LoginRequest.class);
        UserDTO user = userInfoFacade.getUserInfo(UserQueryRequestDTO.builder().username(loginRequest.getUserName()).password(loginRequest.getPassword()).build());
        if (null == user) {
            ctx.writeAndFlush(ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_FAIL_USERNAME_OR_PASSWORD_ERROR));
            return;
        }
        ctx.pipeline().remove(this);
        applicationContext.publishEvent(LoginSuccEventInfo.builder().user(user).channel(ctx.channel()).build());
        ctx.writeAndFlush(ResponseWrapper.loginSuccResponse(LoginResponseEnums.LOGIN_SUCC));
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //super.handlerRemoved(ctx);
        log.info("无登录验证，强制关闭连接！");
    }
}
