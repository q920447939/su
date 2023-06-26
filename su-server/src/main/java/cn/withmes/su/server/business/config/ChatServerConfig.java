/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: ChatServerConfig
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月25日
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "business.chat-server")
public class ChatServerConfig {
    private int port;
    private short magic;
    private int serialize;
}
