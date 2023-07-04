package cn.withmes.su.user;

import lombok.SneakyThrows;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "cn.withmes.su.user")
@EnableConfigurationProperties
@MapperScan(value = "cn.withmes.su.user.business.dao.interfaces")                    //扫描mapper, 需要避免无关接口
@EnableDubbo    // @EnableDubbo 注解必须配置，否则将无法加载 Dubbo 注解定义的服务，@EnableDubbo 可以定义在主类上
//@EnableDiscoveryClient
public class SuUserApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(SuUserApplication.class, args);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
