package com.fly.nio.selector;

import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SocketClientExample {

    public static void main (String [] args)
            throws IOException, InterruptedException {

        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5454);
        SocketChannel client = SocketChannel.open(hostAddress);

        System.out.println("客户端发送消息到服务端...");

        //发送消息到服务端
        String [] messages = new String [] {"时间过得好快", "接下来怎么办呢？", "再见！"};

        for (String message1 : messages) {
            byte[] message = message1.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);

            System.out.println(message1);
            buffer.clear();
            Thread.sleep(3000);
        }

        client.close();
    }
}