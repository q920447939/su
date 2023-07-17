/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月11日
 */
package cn.withmes.su.server.business.utils.channel;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.thread.ThreadUtil;
import cn.withemes.common.redis.RedisUtil;
import cn.withmes.su.server.business.constant.redis.RedisKeyConstant;
import cn.withmes.su.server.business.utils.redis.RedisUtilsWrapper;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话工具
 *
 * @author liming
 * @date 2023/07/11
 */
@Slf4j
@Service
public class SessionUtil {
    @Resource
    private RedisUtilsWrapper redisUtilsWrapper;

    /**
     * 用户会话到期秒数,默认10分钟
     */
    @Value("${business.chat-server.user-session-expire-second:600}")
    private int userSessionExpireSecond;
    /**
     * 用户id通道映射
     */
    private static final Map<Long, Set<Channel>> userIdChannelMap = new ConcurrentHashMap<>();



    /**
     * 绑定会话
     *
     * @param session 会话
     * @param channel 通道
     */
    public  void bindSession(Session session, Channel channel) {
        Long userId = session.getUserId();
        String userSessionSetKey = String.format(RedisKeyConstant.USER_CHANNEL_SESSION_SET_PRE, userId);
        RedisUtil redisUtil = redisUtilsWrapper.getRedisUtil();
        //Set<Object> userChannelSet = redisUtil.sGet(userSessionSetKey);
        redisUtil.sSetAndTime(userSessionSetKey,userSessionExpireSecond*1000L,channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解除绑定会话
     *
     * @param channel 通道
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 已登录
     *
     * @param channel 通道
     * @return boolean
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 获得会话
     *
     * @param channel 通道
     * @return {@link Session}
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }


    /**
     * 获得用户id通道映射
     *
     * @param userId 用户id
     * @return {@link Set}<{@link Channel}>
     */
    public Set<Channel> getUserIdChannelMap(Long userId) {
        return userIdChannelMap.get(userId);
    }
}
