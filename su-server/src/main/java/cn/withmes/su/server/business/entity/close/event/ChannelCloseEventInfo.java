/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.entity.close.event;

import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;

/**
 * 通道关闭事件信息
 * ClassName: ChannelCloseEventInfo
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Data
@Builder
public class ChannelCloseEventInfo {
    private Channel channel;
}
