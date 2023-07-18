/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年04月03日
 */
package cn.withmes.su.server.business.handler.inbound.userlist;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.withemes.common.protocol.ChannelResponseWriteDecorator;
import cn.withmes.su.server.business.decorate.response.ResponseWrapper;
import cn.withmes.su.server.business.entity.userlist.UserListRequest;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.UserListEnums;
import cn.withmes.su.server.business.pack.Package;
import cn.withmes.su.server.business.strategy.serialize.SerializeStrategy;
import cn.withmes.su.server.business.utils.channel.Session;
import cn.withmes.su.server.business.utils.channel.SessionWrapper;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@ChannelHandler.Sharable
@Slf4j
public class UserChannelHandle extends ChannelInboundHandlerAdapter {


    @Resource
    private ChannelResponseWriteDecorator channelResponseWriteDecorator;

    @Resource
    private SerializeStrategy serializeStrategy;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Package packget)) {
            return;
        }
        if (PackageEnums.USER_FRIEND.getType() != packget.getCommand()) {
            super.channelRead(ctx, msg);
            return;
        }
        UserListRequest userListRequest = JSONObject.toJavaObject((JSONObject) packget.getBody(), UserListRequest.class);
        if (null == userListRequest || CollUtil.isEmpty(userListRequest.getTypeList())) {
            log.warn("未获取userlist 枚举类型");
            super.channelRead(ctx, msg);
            return;
        }
        Map<Integer,List> resultMap = new HashMap<>();
        Long userId = SessionWrapper.getSession(ctx.channel()).getUserId();
        for (Integer type : userListRequest.getTypeList()) {
            UserListEnums listEnums = UserListEnums.getEnumsByType(type);
            UserListStrategy listStrategy = SpringUtil.getBean(listEnums.getClz());
            List tmpList = listStrategy.list(ctx, msg, userId);
            resultMap.put(listEnums.getType(),tmpList);
        }
        channelResponseWriteDecorator.writeAndFlush(ctx,ResponseWrapper.succResponse(PackageEnums.USER_FRIEND,serializeStrategy.enc(resultMap)));
    }
}
