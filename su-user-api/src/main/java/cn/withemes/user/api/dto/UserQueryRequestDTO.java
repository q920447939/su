/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月28日
 */
package cn.withemes.user.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserQueryRequestDTO
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月28日
 */
@Data
@Builder
public class UserQueryRequestDTO implements Serializable {
    private String username;
    private String password;
}
