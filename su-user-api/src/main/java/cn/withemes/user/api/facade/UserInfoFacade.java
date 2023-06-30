/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月28日
 */
package cn.withemes.user.api.facade;

import cn.withemes.user.api.dto.UserDTO;
import cn.withemes.user.api.dto.UserQueryRequestDTO;

/**
 * 用户信息门面
 * ClassName: UserInfoFacade
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月28日
 */
public interface UserInfoFacade {
    /**
     * 获得用户信息
     *
     * @param userQueryRequestDTO 用户查询请求dto
     * @return {@link UserDTO}
     */
    UserDTO getUserInfo(UserQueryRequestDTO userQueryRequestDTO);
}
