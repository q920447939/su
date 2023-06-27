package cn.withmes.su.server.business.enums.response.login;

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
public enum LoginResponseEnums implements ResponseEnumsInterface {
    LOGIN_SUCC(20001, "登录成功"),
    LOGIN_FAIL_USERNAME_OR_PASSWORD_ERROR(40001, "用户名或密码错误"),

    ;
    private int code;
    private String desc;

    LoginResponseEnums(int code, String desc) {
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
