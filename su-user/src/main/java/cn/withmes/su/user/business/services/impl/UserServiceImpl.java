package cn.withmes.su.user.business.services.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.withemes.user.api.dto.UserDTO;
import cn.withemes.user.api.dto.UserQueryRequestDTO;
import cn.withemes.user.api.facade.UserInfoFacade;
import cn.withmes.su.user.business.constant.user.UserStatusEnums;
import cn.withmes.su.user.business.dao.interfaces.UserDao;
import cn.withmes.su.user.business.entity.User;
import cn.withmes.su.user.business.services.interfaces.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author leegoo
 * @since 2023-06-26
 */
@Service
@DubboService(interfaceClass = UserInfoFacade.class)
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Override
    public UserDTO getUserInfo(UserQueryRequestDTO userQueryRequestDTO) {
        User user =
                super.baseMapper.selectOne(
                        new LambdaQueryWrapper<User>().eq(User::getUserName, userQueryRequestDTO.getUsername()).eq(User::getPassword, userQueryRequestDTO.getPassword()).eq(User::getStatus, UserStatusEnums.STATUS_NORMAL.getStatus()));
        return null == user ? null : BeanUtil.copyProperties(user,UserDTO.class);
    }
}
