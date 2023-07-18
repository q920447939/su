/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月11日
 */
package cn.withmes.su.server.business.utils.channel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.NetUtil;
import cn.withemes.common.redis.RedisUtil;
import cn.withmes.su.server.business.constant.redis.RedisKeyConstant;
import cn.withmes.su.server.business.event.entity.UserChannelRelation;
import cn.withmes.su.server.business.utils.redis.RedisUtilsWrapper;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 会话工具
 *
 * @author liming
 * @date 2023/07/11
 */
@Slf4j
@Service
public class SessionWrapper {
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
    //private static final Map<Long, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, Channel> channelIdRelationMap = new ConcurrentHashMap<>();



    /**
     * 绑定会话
     *
     * @param session 会话
     * @param channel 通道
     */
    public  void bindSession(Session session, Channel channel) {
        Long userId = session.getUserId();
        /*Channel cacheChannel = userIdChannelMap.get(userId);
        if (null != cacheChannel){
            log.warn("当前用户已绑定channel");
            return;
        }*/
        //此处没有做分布式锁，当连续发送多次登录请求，只会保留一个userChannelRelation,此时不能保证保留的是最后一个请求
        UserChannelRelation relation = new UserChannelRelation();
        String channelId = channel.id().asLongText();
        relation.setIp(NetUtil.getLocalhost().getHostAddress()).setChannelId(channelId).setUserId(userId);
        /*userIdChannelMap.put(userId,channel);*/
        channelIdRelationMap.put(channelId,channel);
        //保存到redis,以便应用部署成分部署时，可以找到所属那台机器
        String userSessionSetKey = String.format(RedisKeyConstant.USER_CHANNEL_SESSION_SET_PRE, userId);
        RedisUtil redisUtil = redisUtilsWrapper.getRedisUtil();
        redisUtil.sSetAndTime(userSessionSetKey,userSessionExpireSecond,relation);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解除绑定会话
     *
     * @param channel 通道
     */
/*    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }*/

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
     * 获得当前机器的 用户id通道映射
     *  通过nginx ，可能这台机器上面没有当前用户信息。也有可能存了当前用户信息
     *  既然最后都要查redis ，是否在其他节点（比如多端登录）。所以直接查redis
     *
     * @param userId 用户id
     * @return {@link Set}<{@link Channel}>
     */
/*    public Channel getUserSessionByCurMachine(Long userId) {
        Set<UserChannelRelation> relationSet = getUserSessionAllMachine(userId);
        if (null == relationSet){
            return null;
        }
        //找到符合本机器的ip数据
        UserChannelRelation userChannelRelation = relationSet.stream().filter(k -> {
            return k.getIp().equals(NetUtil.getLocalhost().getHostAddress());
        }).findFirst().orElse(null);
        if (null == userChannelRelation){
            return null;
        }
        //如果找到了。返回本机器的channel
        return userIdChannelMap.get(userChannelRelation.getUserId());
    }*/

    /**
     * 获得所有机器的 用户id通道映射
     *  通过nginx ，可能这台机器上面没有当前用户信息。也有可能存了当前用户信息
     *  既然最后都要查redis ，是否在其他节点（比如多端登录）。所以直接查redis
     *
     * @param userId 用户id
     * @return {@link Set}<{@link UserChannelRelation}>
     */
    public Set<UserChannelRelation> getUserSessionAllMachine(Long userId) {
        String userSessionSetKey = String.format(RedisKeyConstant.USER_CHANNEL_SESSION_SET_PRE, userId);
        Set<Object> userChannel = redisUtilsWrapper.getRedisUtil().sGet(userSessionSetKey);
        if (CollUtil.isEmpty(userChannel)){
            //未登录
            return null;
        }
        return userChannel.stream().map(k -> (UserChannelRelation) k).collect(Collectors.toSet());
    }



/*    public  Channel getChannelByUserId(Long userId) {
        return userIdChannelMap.get(userId);
    }*/

    public  Channel getChannelByChannelId(String channelId) {
        return channelIdRelationMap.get(channelId);
    }

}
