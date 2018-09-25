package com.fly.nio.selector;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.Set;
import java.util.Iterator;
import java.net.InetSocketAddress;

public class SelectorExample {

    public static void main (String [] args)
            throws IOException {

        // 打开选择器
        Selector selector = Selector.open();

        System.out.println("打开选择器: " + selector.isOpen());

        // 打开服务器套接字通道并注册到选择器
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5454);
        serverSocket.bind(hostAddress);
        serverSocket.configureBlocking(false);
        int ops = serverSocket.validOps();
        serverSocket.register(selector, ops, null);

        for (;;) {

            System.out.println("等待选择...");
            int noOfKeys = selector.select();

            System.out.println("选中的key值数量: " + noOfKeys);

            Set selectedKeys = selector.selectedKeys();
            Iterator iter = selectedKeys.iterator();

            while (iter.hasNext()) {

                SelectionKey ky = (SelectionKey) iter.next();

                if (ky.isAcceptable()) {

                    // 接收新的客户端连接
                    SocketChannel client = serverSocket.accept();
                    client.configureBlocking(false);

                    // 添加新的连接到选择器
                    client.register(selector, SelectionKey.OP_READ);

                    System.out.println("从客户端接收到新的连接: " + client);
                }
                else if (ky.isReadable()) {
                    // 读取客户端数据
                    SocketChannel client = (SocketChannel) ky.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    client.read(buffer);
                    String output = new String(buffer.array()).trim();

                    System.out.println("从客户端读取的消息: " + output);

                    if (output.equals("再见！")) {

                        client.close();
                        System.out.println("客户端消息传输完成，关闭");
                    }

                }

                iter.remove();

            }

        }
    }
}