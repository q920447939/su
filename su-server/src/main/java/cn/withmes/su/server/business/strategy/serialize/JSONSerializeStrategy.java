/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.serialize;

import cn.withmes.su.server.business.enums.SerializeEnums;
import cn.withmes.su.server.business.pack.Package;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

/**
 * ClassName: JSONSerializeStrategy
 *
 * @author leegoo
 * @Description: json序列化
 * @date 2023年06月25日
 */
@Service
public class JSONSerializeStrategy extends AbstractSerialize {
    @Override
    protected SerializeEnums serializeEnums() {
        return SerializeEnums.JSON;
    }

    @Override
    protected Package dec1(ByteBuf source) {
        //return JSON.parseObject(source, SerializeEnums.JSON);
        return null;
    }
}
