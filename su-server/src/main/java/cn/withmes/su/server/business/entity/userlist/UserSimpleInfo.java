package cn.withmes.su.server.business.entity.userlist;

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
public class UserSimpleInfo implements Serializable {

    private static final long serialVersionUID = 1L;
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
     * 头像
     */
    private String avatar;

}
