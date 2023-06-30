package cn.withmes.su.user.business.services.interfaces;

import cn.withemes.user.api.facade.UserInfoFacade;
import cn.withmes.su.user.business.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leegoo
 * @since 2023-06-26
 */
public interface UserService extends IService<User>, UserInfoFacade {

}
