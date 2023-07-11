package cn.withmes.su.server.business.utils.channel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Session {
    // 用户唯一性标识
    private Long userId;
    private String userName;
}
