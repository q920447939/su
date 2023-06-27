/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.enums;

import lombok.Getter;

/**
 * ClassName: ResponseTypeEnums
 * @Description:
 * @author leegoo
 * @date 2023年06月27日
 */
@Getter
public enum ResponseTypeEnums {
    LOGIN(1, "登录"),
    ;
    private  int type;
    private  String desc;

    ResponseTypeEnums(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
