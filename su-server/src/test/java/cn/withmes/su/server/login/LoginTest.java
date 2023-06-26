/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server.login;

import cn.withmes.su.server.ConnectBaseTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * ClassName: LoginTest
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月26日
 */
public class LoginTest extends ConnectBaseTest {
    @Override
    protected void callback1() {

    }

    @Test
    public  void loginTest () {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        buffer.writeShort(chatServerConfig.getMagic());
        buffer.writeShort(1);
        String msg = "{\n" +
                "\t\"command\":\"1\",\n" +
                "\t\"body\":{\n" +
                "\t\t\"userName\":\"sd\",\n" +
                "\t\t\"password\":\"123\"\n" +
                "\t}\n" +
                "}";
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        super.channel.writeAndFlush(buffer);
    }
}
