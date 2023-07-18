/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.serialize;

import cn.withmes.su.server.business.pack.Package;
import io.netty.buffer.ByteBuf;

/**
 * ClassName: SerializeStrategy
 *
 * @author leegoo
 * @Description: 序列化策略
 * @date 2023年06月25日
 */
public interface SerializeStrategy {
    Object enc(Object source);
    Package dec(byte[] source);
}
