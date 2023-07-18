/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月18日
 */
package cn.withmes.su.server.business.event.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ClassName: UserChannelRelation
 *
 * @author leegoo
 * @Description:
 * @date 2023年07月18日
 */
@Data
@Accessors(chain = true)
public class UserChannelRelation {
    private String channelId;
    private String ip;
    private Long userId;

    public UserChannelRelation() {
    }

    public UserChannelRelation(String channelId, Long userId) {
        this.channelId = channelId;
        this.userId = userId;
    }
}
