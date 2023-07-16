/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月11日
 */
package cn.withmes.su.server.business.utils.channel;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.withmes.su.server.business.constant.redis.RedisKeyConstant;
import cn.withmes.su.server.business.utils.LoginUtil;
import cn.withmes.su.server.business.utils.redis.RedisUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public static void bindSession(Session session, Channel channel) {
        Long userId = session.getUserId();
        Set<Channel> channelSet = userIdChannelMap.get(userId);
        if (null == channelSet) {
            String key = String.format(RedisKeyConstant.USER_BIND_SESSION_KEY_PRE, userId);
            RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
            boolean getLockSucc = false;
            try {
                while ( !(getLockSucc = redisUtils.acquireLock(key, 60L)) ) {
                    log.warn("【绑定会话】 acquire lock failed,userId={}", userId);
                    ThreadUtil.safeSleep(1000L);
                }
                channelSet = userIdChannelMap.get(userId);
                if (null == channelSet){
                    channelSet = new ConcurrentHashSet<>();
                }
                channelSet.add(channel);
                userIdChannelMap.put(userId, channelSet);
            } catch (Exception ex) {
                log.error("【绑定会话】 bind session err,userId={}", userId, ex);
            } finally {
                if (getLockSucc){
                    redisUtils.releaseLock(key);
                }
            }
        }
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

    /*    *//**
     * 获得通道
     *
     * @param userId 用户id
     * @return {@link Channel}
     *//*
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }*/
}
