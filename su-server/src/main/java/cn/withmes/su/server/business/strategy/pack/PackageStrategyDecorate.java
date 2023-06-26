/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.pack;

import cn.withmes.su.server.business.pack.Package;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: PackageStrategyDecorate
 *
 * @author leegoo
 * @Description: 数据包处理装饰器
 * @date 2023年06月25日
 */
@Service
public class PackageStrategyDecorate {
    private final Map<Short, PackageStrategy> STRATEGY_TYPE_MAP = new HashMap<>();

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Map<String, PackageStrategy> beansOfType = applicationContext.getBeansOfType(PackageStrategy.class);
        for (String beanName : beansOfType.keySet()) {
            PackageStrategy packageStrategy = beansOfType.get(beanName);
            STRATEGY_TYPE_MAP.put((short) packageStrategy.getPackageEnums().getType(), packageStrategy);
        }
    }

    public Package handler(Package aPackage) {
        return this.handler(aPackage.getCommand(), aPackage);
    }

    public Package handler(Short type, Package byteBuf) {
        return STRATEGY_TYPE_MAP.get(type).handle(byteBuf);
    }
}

