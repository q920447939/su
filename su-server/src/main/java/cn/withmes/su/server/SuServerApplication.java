package cn.withmes.su.server;

import cn.withmes.su.server.business.ChatServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "cn.withmes.su.server"
       // ,exclude = {DataSourceAutoConfiguration.class}
)
@EnableConfigurationProperties
@MapperScan(value = "cn.withmes.su.server.business.dao.interfaces")                    //扫描mapper, 需要避免无关接口
public class SuServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SuServerApplication.class, args);
        ChatServer chatServer = context.getBean(ChatServer.class);
        chatServer.start();
    }

}
