/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server.business.entity.login;

import lombok.Data;

/**
 * ClassName: LoginRequest
 *
 * @author leegoo
 * @Description: 登录请求实体
 * @date 2023年06月26日
 */
@Data
public class LoginRequest {
    private String userName;
    private String password;
}
