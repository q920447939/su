/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月16日
 */
package cn.withmes.su.server.business.strategy.password;

import cn.hutool.core.util.StrUtil;
import cn.withmes.su.server.business.entity.User;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * ClassName: UserSpecialPasswordGeneratorStrategy
 *
 * @author leegoo
 * @Description: 根据用户密码策略
 * @date 2023年06月16日
 */
@Service
public class UserSpecialPasswordGeneratorStrategy implements PasswordGeneratorStrategy {

    @Override
    public String getGeneratorInitPasswordPlaintext(User user) {
        return String.valueOf(user.getUserName().charAt(0)).toUpperCase(Locale.ROOT) + user.getUserName().substring(1) + "@123456.";
    }

    @Override
    public String generatorInitPassword(User user) {
        return null;
    }

    @Override
    public boolean isInitPassword(User user, String password) {
        return StrUtil.equals(generatorInitPassword(user), password);
    }
}
