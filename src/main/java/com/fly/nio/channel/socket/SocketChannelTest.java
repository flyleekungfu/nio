package com.fly.nio.channel.socket;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 功能描述：套接字channel测试（TCP客户端）
 * 创建时间：2018/9/24 23:43
 *
 * @author： lifei
 */
public class SocketChannelTest {
    public static void main(String[] args) {
        try {
            // 打开一个通道
            SocketChannel socketChannel = SocketChannel.open();
            // 发起连接
            socketChannel.connect(new InetSocketAddress("https://www.baidu.com/", 80));

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String str = "这是TCP客户端";
            buffer.put(str.getBytes());

            //读取数据
            socketChannel.read(buffer);

            //写入数据到网络连接
            while(buffer.hasRemaining()){
                socketChannel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
