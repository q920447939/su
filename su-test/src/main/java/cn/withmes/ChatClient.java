package cn.withmes;

import com.alibaba.fastjson2.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {

    static final String HOST = "127.0.0.1";
    static final int PORT = 8081;

    public static void main(String[] args) throws Exception {

        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ClientHandler());
                            //pipeline.addLast(new StringEncoder());
                            // 添加更多的 Handler
                        }
                    });

            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();

            // 创建读取用户输入的 Scanner
            Scanner scanner = new Scanner(System.in);

            while (true) {

                // 打印提示菜单
                System.out.println("请输入:");
                System.out.println("1 - 登录");
                System.out.println("2 - 发送消息");

                String input = scanner.next();

                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                buffer.writeShort(0x410F);
                if ("1".equals(input)) {
                    // 登录逻辑
                    System.out.print("账号:");
                    String username = scanner.next();
                    System.out.print("密码:");
                    String password = scanner.next();

                    // 构造登录请求对象
                    LoginRequest request = new LoginRequest(username, password);
                    Package packages = new Package();
                    packages.setCommand((short) 1);
                    packages.setBody(request);

                    buffer.writeShort(1);
                    String msg = JSONObject.toJSONString(packages);
                    byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                    buffer.writeInt(bytes.length);
                    buffer.writeBytes(bytes);
                    // 编码请求对象
                    channel.writeAndFlush(buffer);

                } else if ("2".equals(input)) {
                    // 发送消息逻辑
                    System.out.print("消息内容:");
                    String content = scanner.next();

                    // 构造聊天消息对象
                    ChatRequest message = new ChatRequest(content);

                    // 编码消息对象
                    channel.writeAndFlush(message);

                } else {
                    System.out.println("无效输入!");
                }

            }

        } finally {
            group.shutdownGracefully();
        }

    }

}

// 请求对象实体类


@Data
@AllArgsConstructor
class ChatRequest {
    String content;
    // 构造方法、getter/setter略
}


@Data
class Package {
    private short command;
    private Object body;
}


@Data
@AllArgsConstructor
class LoginRequest {
    private String userName;
    private String password;
}

