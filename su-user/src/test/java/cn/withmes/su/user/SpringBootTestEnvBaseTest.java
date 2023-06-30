/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: EnvBaseTest
 * @Description:
 * @author leegoo
 * @date 2023年06月26日
 */
@SpringBootTest(classes = SuUserApplication.class, properties = {"spring.profiles.active=dev", "log4j-env=dev"})
@Slf4j
@RunWith(SpringRunner.class)
public class SpringBootTestEnvBaseTest implements ApplicationContextAware {
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
