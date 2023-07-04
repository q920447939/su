/*
package cn.withmes.su.user.business.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;


@RefreshScope
@Service
@Slf4j
public class GetConfig implements ApplicationRunner {
    @Value(value = "${test}")
    public String test;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("test1={}", test);
        Thread.sleep(1000 * 20);
        log.info("test2={}", test);
    }
}
*/
