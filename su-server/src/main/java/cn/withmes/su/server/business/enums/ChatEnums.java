package cn.withmes.su.server.business.enums;

import cn.withmes.su.server.business.handler.inbound.chat.SingleChatChannelHandle;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.NonNull;


@Getter
public enum ChatEnums {
    SINGLE(1, "单聊", SingleChatChannelHandle.class),
    MUTI(2, "群发", SingleChatChannelHandle.class);
    private int type;
    private String desc;
    private Class<? extends ChannelInboundHandlerAdapter> clz;

    ChatEnums(int type, String desc ,Class<? extends ChannelInboundHandlerAdapter> clz) {
        this.type = type;
        this.desc = desc;
        this.clz = clz;
    }

    public static ChatEnums getEnumsByType(@NonNull int type ) {
        for (ChatEnums enums : ChatEnums.values()) {
            if (enums.type == type) {
                return enums;
            }
        }
        return null;
    }
}
