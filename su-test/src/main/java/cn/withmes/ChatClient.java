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
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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
                System.out.println("2 - 单聊");
                System.out.println("3 - 获取用户列表");

                String input = scanner.next();

                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                buffer.writeShort(0x410F);
                //版本号
                buffer.writeShort(1);
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

                    String msg = JSONObject.toJSONString(packages);
                    byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                    buffer.writeInt(bytes.length);
                    buffer.writeBytes(bytes);
                    // 编码请求对象
                    channel.writeAndFlush(buffer);

                } else if ("2".equals(input)) {
                    // 发送消息逻辑
                    System.out.print("输入发送者id@消息内容(例如：123@你好)");
                    String chatInput = scanner.next();
                    if ("".equals(chatInput)){
                        System.out.println("消息内容不能为空");
                        continue;
                    }
                    String[] chatInputArray = chatInput.split("@");
                    if (chatInputArray.length != 2){
                        System.out.println("消息内容格式错误");
                        continue;
                    }
                    String senderId = chatInputArray[0];
                    String context = chatInputArray[1];
                    // 构造聊天消息对象
                    Long toUid = null;
                    try {
                        toUid = Long.valueOf(senderId);
                    } catch (NumberFormatException e) {
                        System.out.println("输入接收者id错误");
                        continue;
                    }
                    ChatRequest message = new ChatRequest(1, toUid,context);

                    Package packages = new Package();
                    packages.setCommand((short)2);
                    packages.setBody(message);
                    String msg = JSONObject.toJSONString(packages);
                    byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                    buffer.writeInt(bytes.length);
                    buffer.writeBytes(bytes);
                    // 编码消息对象
                    channel.writeAndFlush(buffer);
                } else if ("3".equals(input)) {
                    // 发送消息逻辑
                    System.out.print("获取用户好友列表开始");
                    Package packages = new Package();
                    packages.setCommand((short)3);
                    UserListRequest  userListRequest = new UserListRequest();
                    userListRequest.setTypeList(Arrays.asList(1));
                    packages.setBody(userListRequest);
                    String msg = JSONObject.toJSONString(packages);
                    byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                    buffer.writeInt(bytes.length);
                    buffer.writeBytes(bytes);
                    // 编码消息对象
                    channel.writeAndFlush(buffer);
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
    private Integer chatType;
    private Long toUid;
    private String message;
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

@Data
@Accessors(chain = true)
class UserListRequest {
    private List<Integer> typeList;
}


