/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月16日
 */
package cn.withmes.su.server.business.strategy.password;

import cn.withmes.su.server.business.entity.User;
import cn.withmes.su.server.utils.MD5Utils;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultPasswordGeneratorStrategy
 *
 * @author leegoo
 * @Description: 默认密码策略
 * @date 2023年06月16日
 */
@Service
public class DefaultPasswordGeneratorStrategy implements PasswordGeneratorStrategy {
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";
    /**
     * 默认密码md5
     */
    public static final String DEFAULT_PASSWORD_MD5 = MD5Utils.md5(DEFAULT_PASSWORD);

    @Override
    public String getGeneratorInitPasswordPlaintext(User user) {
        return DEFAULT_PASSWORD;
    }

    @Override
    public String generatorInitPassword(User user) {
        return null;
    }

    @Override
    public boolean isInitPassword(User user, String password) {
        return false;
    }
}
