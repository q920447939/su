package cn.withmes.su.user.business.constant.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 * ClassName: UserStatusEnums
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月29日
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnums {
    STATUS_NORMAL(1,"正常"),
    STATUS_DISABLE(2,"禁用"),
    STATUS_DELETE(3,"已删除"),
    ;
    private int status;
    private String desc ;


}
