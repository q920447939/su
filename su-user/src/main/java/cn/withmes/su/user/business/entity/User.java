package cn.withmes.su.user.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id,仅用于识别用户，以及做关联
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 注册渠道（1:APP注册，2：微信注册）
     */
    @TableField("channel")
    private Byte channel;

    /**
     * 状态(1:正常,2:禁用,3:已删除)
     */
    @TableField("status")
    private Byte status;

    /**
     * 上次登录时间
     */
    @TableField("last_login")
    private LocalDateTime lastLogin;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String NICK_NAME = "nick_name";

    public static final String PASSWORD = "password";

    public static final String EMAIL = "email";

    public static final String PHONE = "phone";

    public static final String AVATAR = "avatar";

    public static final String CHANNEL = "channel";

    public static final String STATUS = "status";

    public static final String LAST_LOGIN = "last_login";

    public static final String CREATED_AT = "created_at";
}
