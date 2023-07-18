package cn.withmes.su.server.business.enums;

import cn.withmes.su.server.business.handler.inbound.userlist.UserFriendImpl;
import cn.withmes.su.server.business.handler.inbound.userlist.UserListStrategy;
import lombok.Getter;
import lombok.NonNull;


@Getter
public enum UserListEnums {
    SINGLE(1, "好友", UserFriendImpl.class),

    ;
    private int type;
    private String desc;
    private Class<? extends UserListStrategy> clz;

    UserListEnums(int type, String desc, Class<? extends UserListStrategy> clz) {
        this.type = type;
        this.desc = desc;
        this.clz = clz;
    }

    public static UserListEnums getEnumsByType(@NonNull int type) {
        for (UserListEnums enums : UserListEnums.values()) {
            if (enums.type == type) {
                return enums;
            }
        }
        return null;
    }
}
