/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月11日
 */
package cn.withmes.su.server.business.utils.redis;

import cn.hutool.extra.spring.SpringUtil;
import cn.withemes.common.redis.RedisUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName: RedisUtils
 *
 * @author leegoo
 * @Description:
 * @date 2023年07月11日
 */

@Component
public class RedisUtils {

    @Value(value = "spring.application.name")
    private String applicationName;

    /**
     * 获取分布式锁
     *
     * @param lockKey    锁的key
     * @param expireTime 锁的过期时间，单位：秒
     * @return true表示获取锁成功，false表示获取锁失败
     */
    public boolean acquireLock(String lockKey, long expireTime) {
        return SpringUtil.getBean(RedisUtil.class).acquireLock(lockKey, applicationName, expireTime);
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁的key
     * @return true表示释放锁成功，false表示释放锁失败
     */
    public boolean releaseLock(String lockKey) {
        return SpringUtil.getBean(RedisUtil.class).releaseLock(lockKey, applicationName);

    }

}
