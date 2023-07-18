/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月18日
 */
package cn.withmes.su.server.business.handler.inbound.userlist;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.withemes.user.api.dto.UserDTO;
import cn.withemes.user.api.facade.UserInfoFacade;
import cn.withmes.su.server.business.entity.userlist.UserSimpleInfo;
import io.netty.channel.ChannelHandlerContext;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserFriendImpl implements UserListStrategy<UserSimpleInfo> {
    @DubboReference
    private UserInfoFacade userInfoFacade;

    @Override
    public List<UserSimpleInfo> list(ChannelHandlerContext ctx, Object msg, Long userId) {
        List<UserDTO> list = userInfoFacade.getUserFriend(userId);
        return CollUtil.isEmpty(list) ? Collections.emptyList() : BeanUtil.copyToList(list, UserSimpleInfo.class);
    }
}
