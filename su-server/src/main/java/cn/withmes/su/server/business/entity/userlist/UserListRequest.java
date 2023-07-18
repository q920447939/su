/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server.business.entity.userlist;

import lombok.Data;

import java.util.List;

/**
 * 用户列表请求
 *
 * @author liming
 * @date 2023/07/18
 */
@Data
public class UserListRequest {
    private List<Integer> typeList;
}
