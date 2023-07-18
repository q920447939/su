package cn.withmes.su.server.business.enums.response;

import lombok.Getter;


@Getter
public enum BaseResponseEnums implements ResponseEnumsInterface {
    SUCCESS(20001, "成功"),

    ;
    private int code;
    private String desc;

    BaseResponseEnums(int code, String desc) {
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
