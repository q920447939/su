/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.serialize;

import cn.withmes.su.server.business.enums.SerializeEnums;
import cn.withmes.su.server.business.pack.Package;
import com.alibaba.fastjson.JSONObject;
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
    protected Object enc1(Object source) {
        return JSONObject.toJSONString(source);
    }

    @Override
    protected Package dec1(byte[] source) {
        JSONObject jsonObject = JSONObject.parseObject(new String(source));
        if (null == jsonObject){
            throw new RuntimeException("json is null");
        }
        Package aPackage = new Package();
        aPackage.setCommand(jsonObject.getShort("command"));
        aPackage.setBody(jsonObject.getObject("body",Object.class));
        return aPackage;
    }


}
