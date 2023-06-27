/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.event.close;

import cn.withmes.su.server.business.entity.close.event.ChannelCloseEventInfo;
import cn.withmes.su.server.business.entity.login.evnet.LoginSuccEventInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * 客户端关闭事件
 *
 * @author liming
 * @date 2023/06/27
 */
@Component
@Slf4j
public class ClientCloseEvent {
    @Async
    @EventListener
    public void event(@NonNull ChannelCloseEventInfo event) {
        log.info("【客户端关闭事件】 获取到的数据源 event={}", event);

    }
}
