/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月11日
 */
package cn.withmes.su.server.business.constant.redis;

/**
 * ClassName: RedisKeyConstant
 * @Description:
 * @author leegoo
 * @date 2023年07月11日
 */
public class RedisKeyConstant {
    /**
     * 用户绑定会话锁定键前
     */
    public static final String USER_BIND_SESSION_LOCK_KEY_PRE = "USER_BIND_SESSION_LOCK_KEY_%s";
    /**
     * 用户通道会话缓存key
     */
    public static final String USER_CHANNEL_SESSION_SET_PRE = "USER:CHANNEL:SESSION_SET_%s";
}
