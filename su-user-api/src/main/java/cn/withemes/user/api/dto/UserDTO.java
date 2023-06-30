package cn.withemes.user.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author leegoo
 * @since 2023-06-26
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id,仅用于识别用户，以及做关联
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册渠道（1:APP注册，2：微信注册）
     */
    private Byte channel;

    /**
     * 状态(1:正常,2:禁用,3:已删除)
     */
    private Byte status;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
