/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.pack;

import cn.withmes.su.server.business.entity.User;
import cn.withmes.su.server.business.entity.login.LoginRequest;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.pack.Package;
import cn.withmes.su.server.business.services.interfaces.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * ClassName: LoginRequestPackageStrategy
 *
 * @author leegoo
 * @Description: 登录请求数据包处理
 * @date 2023年06月25日
 */
@Service
public class LoginRequestPackageStrategy implements PackageStrategy {
    @Resource
    private UserService userService;

    @Override
    public PackageEnums getPackageEnums() {
        return PackageEnums.LOGIN;
    }

    @Override
    public Package handle(Package aPackage) {
        LoginRequest loginRequest = JSONObject.toJavaObject((JSONObject) aPackage.getBody(),LoginRequest.class);
        boolean b = this.validDate(loginRequest);
        return null;
    }

    private boolean validDate(LoginRequest loginRequest) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, loginRequest.getUserName()).eq(User::getPassword, loginRequest.getPassword()));
        return null == user;
    }
}
