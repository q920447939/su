package cn.withmes.su.server.business.enums;

import lombok.Getter;
import lombok.NonNull;


@Getter
public enum PackageEnums {
    LOGIN(1, "登录"),
    CHAT(2, "聊天"),
    USER_FRIEND(3, "用户列表"),

    HEAR_BEAT(30000, "心跳"),
    ;
    private int type;
    private String desc;

    PackageEnums(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static PackageEnums getPackageEnumsByType(@NonNull int type ) {
        for (PackageEnums enums : PackageEnums.values()) {
            if (enums.type == type) {
                return enums;
            }
        }
        return null;

    }
}
