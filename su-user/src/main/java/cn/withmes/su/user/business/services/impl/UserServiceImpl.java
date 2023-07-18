package cn.withmes.su.user.business.services.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.withemes.user.api.dto.UserDTO;
import cn.withemes.user.api.dto.UserQueryRequestDTO;
import cn.withemes.user.api.facade.UserInfoFacade;
import cn.withmes.su.user.business.constant.user.UserStatusEnums;
import cn.withmes.su.user.business.dao.interfaces.UserDao;
import cn.withmes.su.user.business.dao.interfaces.UserFriendMapper;
import cn.withmes.su.user.business.entity.User;
import cn.withmes.su.user.business.entity.UserFriend;
import cn.withmes.su.user.business.services.interfaces.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private UserFriendMapper userFriendMapper;

    @Override
    public UserDTO getUserInfo(UserQueryRequestDTO userQueryRequestDTO) {
        User user =
                super.baseMapper.selectOne(
                        new LambdaQueryWrapper<User>().eq(User::getUserName, userQueryRequestDTO.getUsername()).eq(User::getPassword, userQueryRequestDTO.getPassword()).eq(User::getStatus, UserStatusEnums.STATUS_NORMAL.getStatus()));
        return null == user ? null : BeanUtil.copyProperties(user,UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserFriend(Long userId) {
        List<UserFriend> userFriendList = userFriendMapper.selectList(new LambdaQueryWrapper<UserFriend>().eq(UserFriend::getUserId, userId));
        if (CollUtil.isEmpty(userFriendList)){
            return Collections.emptyList();
        }
        List<Long> friendIds = userFriendList.stream().map(UserFriend::getFriendId).toList();

        List<User> users = super.baseMapper.selectList(new LambdaQueryWrapper<User>().in(User::getUserId, friendIds));
        return null == users ? Collections.emptyList() : BeanUtil.copyToList(users,UserDTO.class);
    }
}
