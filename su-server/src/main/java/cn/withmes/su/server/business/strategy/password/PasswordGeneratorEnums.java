package cn.withmes.su.server.business.strategy.password;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * ClassName: PasswordGeneratorEnums
 *
 * @author leegoo
 * @Description: 密码生成策略模式
 * @date 2023年06月16日
 */
@Getter
@AllArgsConstructor
public enum PasswordGeneratorEnums {
    USER_SPECIAL_PASSWORD_GENERATOR_STRATEGY("1", "用户账号+密码模式(@123456.)", UserSpecialPasswordGeneratorStrategy.class),
    DEFAULT_PASSWORD_GENERATOR_STRATEGY("2", "默认策略，密码 123456", DefaultPasswordGeneratorStrategy.class);
    private String type;

    private String desc;
    private Class<? extends PasswordGeneratorStrategy> target;

    /**
     * 获得密码生成策略通过类型
     *
     * @param type 类型
     * @return {@link Class}<{@link ?} {@link extends} {@link PasswordGeneratorStrategy}>
     */
    public static Class<? extends PasswordGeneratorStrategy> getPasswordGeneratorStrategyByType(String type) {
        return Arrays.stream(PasswordGeneratorEnums.values()).filter(k -> k.getType().equals(type)).findFirst().orElseThrow(
                () -> {
                    return new RuntimeException("获取密码策略失败");
                }).getTarget();
    }

}
