/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月18日
 */
package cn.withmes.su.server.business.handler.inbound.userlist;


import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface UserListStrategy<T> {
    List<T> list(ChannelHandlerContext ctx, Object msg,Long userId);
}
