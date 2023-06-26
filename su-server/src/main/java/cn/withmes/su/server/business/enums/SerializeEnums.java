package cn.withmes.su.server.business.enums;

import lombok.Getter;

/**


/**
 * ClassName: SerializeEnums
 * @Description:
 * @author leegoo
 * @date 2023年06月25日
 */
@Getter
public enum SerializeEnums {
    JSON(1, "JSON");
    private int type;
    private String desc;

    SerializeEnums(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
