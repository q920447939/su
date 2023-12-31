/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.entity.login.evnet;

import cn.withemes.user.api.dto.UserDTO;
import cn.withmes.su.server.business.entity.User;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;

/**
 * 登录succ事件信息
 * ClassName: LoginSuccEventInfo
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Builder
@Data
public class LoginSuccEventInfo {
    private UserDTO user;
    private Channel channel;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
