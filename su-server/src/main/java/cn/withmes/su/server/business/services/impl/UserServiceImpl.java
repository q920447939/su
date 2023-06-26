package cn.withmes.su.server.business.services.impl;

import cn.withmes.su.server.business.entity.User;
import cn.withmes.su.server.business.dao.interfaces.UserDao;
import cn.withmes.su.server.business.services.interfaces.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leegoo
 * @since 2023-06-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
