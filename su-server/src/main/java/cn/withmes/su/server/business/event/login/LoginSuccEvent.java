/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.event.login;

import cn.withemes.user.api.dto.UserDTO;
import cn.withmes.su.server.business.entity.login.evnet.LoginSuccEventInfo;
import cn.withmes.su.server.business.utils.LoginUtil;
import cn.withmes.su.server.business.utils.channel.Session;
import cn.withmes.su.server.business.utils.channel.SessionUtil;
import io.netty.channel.Channel;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 登录succ事件
 * ClassName: LoginSuccEvent
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Component
@Slf4j
public class LoginSuccEvent {
    //@Async
    @EventListener
    public void loginSucc(@NonNull LoginSuccEventInfo event) {
        log.info("【用户登录成功】 获取到的数据源 event={}", event);
        Session session = new Session();
        UserDTO user = event.getUser();
        session.setUserId(user.getUserId()).setUserName(user.getUserName())	;
        Channel channel = event.getChannel();
        SessionUtil.bindSession(session, channel);
        LoginUtil.markAsLogin(channel);
    }
}
