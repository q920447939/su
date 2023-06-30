/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 *//*

package cn.withmes.su.server.business.strategy.pack;

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
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

*/
/**
 * ClassName: LoginRequestPackageStrategy
 *
 * @author leegoo
 * @Description: 登录请求数据包处理
 * @date 2023年06月25日
 *//*

@Service
public class LoginRequestPackageStrategy implements PackageStrategy {
    @Resource
    private UserService userService;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public PackageEnums getPackageEnums() {
        return PackageEnums.LOGIN;
    }

    @Override
    public Package handle(ChannelHandlerContext ctx, Package aPackage) {
        LoginRequest loginRequest = JSONObject.toJavaObject((JSONObject) aPackage.getBody(), LoginRequest.class);
        User user = this.login(loginRequest);
        if (null == user) {
            ctx.writeAndFlush(ResponseWrapper.loginFailResponse(LoginResponseEnums.LOGIN_FAIL_USERNAME_OR_PASSWORD_ERROR));
            return null;
        }
        LoginUtil.markAsLogin(ctx.channel());
        applicationContext.publishEvent(LoginSuccEventInfo.builder().user(user).build());
        ctx.writeAndFlush(ResponseWrapper.loginSuccResponse(LoginResponseEnums.LOGIN_SUCC));
        return null;
    }

    private User login(LoginRequest loginRequest) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, loginRequest.getUserName()).eq(User::getPassword, loginRequest.getPassword()));
        return user;
    }
}
*/
