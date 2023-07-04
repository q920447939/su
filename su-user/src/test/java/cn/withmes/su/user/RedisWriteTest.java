/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月30日
 */
package cn.withmes.su.user;

import cn.hutool.core.util.IdUtil;
import cn.withmes.su.user.utils.redis.RedisUtil;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * ClassName: RedisWriteTest
 * @Description:
 * @author leegoo
 * @date 2023年06月30日
 */
public class RedisWriteTest extends SpringBootTestEnvBaseTest{

    @Resource
    protected RedisUtil redisUtil;

    @Test
    public void write () {
        String key = IdUtil.fastSimpleUUID();
        Assert.assertTrue(redisUtil.set(key, key,60));
    }
}
