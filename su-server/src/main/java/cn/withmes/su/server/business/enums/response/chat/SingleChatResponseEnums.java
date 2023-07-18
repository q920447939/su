package cn.withmes.su.server.business.enums.response.chat;

import cn.withmes.su.server.business.enums.response.ResponseEnumsInterface;
import lombok.Getter;

/**
 * ClassName: LoginResponseEnums
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Getter
public enum SingleChatResponseEnums implements ResponseEnumsInterface {
    CHAT_MESSAGE_SUCCESS(20001, "聊天信息发送成功"),
    CHAT_MESSAGE_SUCCESS1(20002, "聊天信息发送成功（自生）"),
    CHAT_TO_USER_OFFLINE(40001, "用户不在线"),
    UN_LOGIN(40002, "未登录"),

    ;
    private int code;
    private String desc;

    SingleChatResponseEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String desc() {
        return this.desc;
    }
}
