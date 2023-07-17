/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月17日
 */
package cn.withemes.common.protocol;


import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class BytBufUtils {

    /**
     * @param obj
     * @return {@link ByteBuf}
     */
    public static ByteBuf objToByteBuf(Object obj){
        return Unpooled.wrappedBuffer(JSONObject.toJSONString(obj).getBytes(StandardCharsets.UTF_8));
    }
}
