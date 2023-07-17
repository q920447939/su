/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server.business.entity.response;

import lombok.Builder;
import lombok.Data;


/**
 * 聊天反应
 *
 * @author liming
 * @date 2023/07/16
 */
@Data
@Builder
public class ChatResponse {
    private Long fromUid;
    private String fromNickName;
    private Long toUid;
    private String toNickName;
    private String message;
}
