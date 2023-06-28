/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月28日
 */
package cn.withmes.su.server.business.handler.inbound;

import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.User;
import cn.withmes.su.server.business.entity.login.LoginRequest;
import cn.withmes.su.server.business.entity.login.evnet.LoginSuccEventInfo;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.response.login.LoginResponseEnums;
import cn.withmes.su.server.business.pack.Package;
import cn.withmes.su.server.business.services.interfaces.UserService;
import cn.withmes.su.server.business.utils.LoginUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jakarta.annotation.Resource;
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
public class LoginRequestHandle extends SimpleChannelInboundHandler<Package> {
    @Resource
    private UserService userService;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Package msg) throws Exception {
        if (PackageEnums.LOGIN.getType() != msg.getCommand()){
            ctx.writeAndFlush(ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_COMMAND_IS_ERROR));
            return;
        }
        LoginRequest loginRequest = JSONObject.toJavaObject((JSONObject) msg.getBody(), LoginRequest.class);
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, loginRequest.getUserName()).eq(User::getPassword, loginRequest.getPassword()));
        if (null == user) {
            ctx.writeAndFlush(ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_FAIL_USERNAME_OR_PASSWORD_ERROR));
            return;
        }
        ctx.pipeline().remove(this);
        LoginUtil.markAsLogin(ctx.channel());
        applicationContext.publishEvent(LoginSuccEventInfo.builder().user(user).build());
        ctx.writeAndFlush(ResponseWrapper.loginSuccResponse(LoginResponseEnums.LOGIN_SUCC));
        super.channelRead(ctx,msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
