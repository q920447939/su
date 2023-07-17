/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server.business.entity.chat;

import lombok.Data;

/**
 * 聊天请求
 *
 * @author liming
 * @date 2023/07/16
 */
@Data
public class ChatRequest {
    private Integer chatType;
    private Long toUid;
    private String message;
}
