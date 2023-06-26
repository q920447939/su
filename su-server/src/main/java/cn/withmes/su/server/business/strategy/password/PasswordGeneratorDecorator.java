/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月16日
 */
package cn.withmes.su.server.business.strategy.password;

import cn.withmes.su.server.business.entity.User;
import org.springframework.stereotype.Service;

/**
 * ClassName: PasswordGeneratorDecorator
 *
 * @author leegoo
 * @Description: 密码策略装饰者
 * @date 2023年06月16日
 */
@Service
public class PasswordGeneratorDecorator {

    private PasswordGeneratorStrategy getPasswordGeneratorStrategy() {
        return null;
    }

    public String generatorInitPassword(User user) {
        return null;
    }

    public boolean isInitPassword(User user, String password) {
        return false;
    }
}
