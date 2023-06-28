/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.serialize;

import cn.withmes.su.server.business.config.ChatServerConfig;
import cn.withmes.su.server.business.enums.SerializeEnums;
import cn.withmes.su.server.business.pack.Package;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;


/**
 * ClassName: AbstractSerialize
 *
 * @author leegoo
 * @Description: 序列化抽象类
 * @date 2023年06月25日
 */
@Slf4j
public abstract class AbstractSerialize implements ApplicationContextAware, SerializeStrategy, Condition {

    protected abstract SerializeEnums serializeEnums();

    protected abstract Package dec1(byte[] source);

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ChatServerConfig chatServerConfig = this.applicationContext.getBean(ChatServerConfig.class);
        SerializeEnums serializeEnums = Arrays.stream(SerializeEnums.values()).filter(k -> k.getType() == (chatServerConfig.getSerialize())).findFirst().orElseThrow(() -> {
            throw new RuntimeException("未知的序列化类型");
        });
        boolean b = serializeEnums == this.serializeEnums();
        if (b) {
            log.info("序列化类型为：{}", serializeEnums.getDesc());
        }
        return b;
    }

    @Override
    public Package enc(ByteBuf source) {
        return null;
    }

    @Override
    public Package dec(byte[] source) {
        return this.dec1(source);
    }
}
