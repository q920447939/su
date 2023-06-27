/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.utils;

import cn.withmes.su.server.business.utils.channel.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;


/**
 * 登录工具
 * ClassName: LoginUtil
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
public class LoginUtil {

    /**
     * 标记作为登录
     *
     * @param channel 通道
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 已登录
     *
     * @param channel 通道
     * @return boolean
     */
    public static boolean isLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        Boolean isLogin = loginAttr.get();
        return null != isLogin && isLogin;
    }
}
