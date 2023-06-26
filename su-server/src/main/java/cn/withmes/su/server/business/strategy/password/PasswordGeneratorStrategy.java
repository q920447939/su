/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月16日
 */
package cn.withmes.su.server.business.strategy.password;


import cn.withmes.su.server.business.entity.User;

/**
 * ClassName: PasswordGeneratorStrategy
 * @Description: 密码生成策略
 * @author leegoo
 * @date 2023年06月16日
 */
public interface PasswordGeneratorStrategy {
    /**
     * 获得初始化密码明文
     *
     * @param user 用户信息实体
     * @return {@link String}
     */
    String getGeneratorInitPasswordPlaintext(User user);
    /**
     * 生成初始化密码
     *
     * @param user 用户信息实体
     * @return {@link String}
     */
    String generatorInitPassword(User user);

    /**
     * 是否初始化密码
     *
     * @param user 用户信息实体
     * @param password       密码
     * @return boolean
     */
    boolean isInitPassword(User user,String password);
}
