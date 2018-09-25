package com.fly.nio.channel.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述：文件通道测试类，写入字符串到文件
 * 创建时间：2018/9/23 19:21
 *
 * @author： lifei
 */
public class FileChannelTest {
    public static void main(String[] args) {
        String filePath = "D:\\test\\nio.txt";
        String content = "java nio is me";
        try {
//            writeFile(filePath,content);
            readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param  filePath 文件路径
     * @param  content 写入的内容
     * @return void
     * @author lifei
     * @date 2018-09-23 23:58:07
     */
    private static void writeFile(String filePath,String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(filePath));
        FileChannel channel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(content.getBytes());

        //此处必须要调用buffer的flip方法
        buffer.flip();
        channel.write(buffer);

        channel.close();
        outputStream.close();

        System.out.println("写入文件成功！");
    }

    private static void readFile(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(new File(filePath));
        FileChannel inChannel =  in.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buffer);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buffer.flip();

            while(buffer.hasRemaining()){
                System.out.print((char) buffer.get());
            }

            buffer.clear();
            bytesRead = inChannel.read(buffer);
        }
        in.close();
    }
}
