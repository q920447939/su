package cn.withmes.su.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "cn.withmes.su.user")
@EnableConfigurationProperties
@MapperScan(value = "cn.withmes.su.user.business.dao.interfaces")                    //扫描mapper, 需要避免无关接口
@EnableDubbo
public class SuUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuUserApplication.class, args);
    }

}
