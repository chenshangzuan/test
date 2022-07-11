package java_base.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Kled
 * @date 2022/4/26 10:12 AM
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        //bufferTest();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));

        ByteBuffer buffer = ByteBuffer.allocate(50);
        byte[] data = {'H', 'E', 'L', 'L', 'O'};
        buffer.put(data);
        buffer.flip();
        socketChannel.write(buffer);

        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        byte[] res = new byte[buffer.remaining()];
        buffer.get(res);
        System.out.println("NioClient -> get response：" + new String(res));

        //关闭客户端输入流
        socketChannel.socket().shutdownInput();
        //关闭客户端输出流
        socketChannel.socket().shutdownOutput();
        //关闭客户端socket时会关闭客户端channel
        socketChannel.socket().close();
        //关闭客户端channel，会同时关闭输入和输出流。
        socketChannel.close();
    }

    public static void fileChannel() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();

        FileInputStream inputStream = new FileInputStream("1.txt");
        FileChannel inputChannel  = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("1.txt");
        FileChannel outputChannel  = outputStream.getChannel();

        //关闭channel时会关闭文件
        channel.close();
        //关闭文件时会关闭channel
        randomAccessFile.close();
        //关闭文件流时会关闭channel
        inputStream.close();
        //关闭文件流时会关闭channel
        inputStream.close();

    }

    public static void bufferTest() {
        byte[] data = new byte[]{'H', 'E', 'L', 'L', 'O'};
        System.out.println(new String(data));
        //ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put(data);

        //初始化指针，转为读模式
        byteBuffer.flip();
        byte[] data1 = new byte[3];
        byteBuffer.get(data1);
        System.out.println(new String(data1));

        //缓存切片，新建缓存但还是指向原来的buffer，通过调整offset和容量来构造新视图
        ByteBuffer sliceByteBuffer = byteBuffer.slice();
        sliceByteBuffer.compact();
        //重置position=0,mark=-1
        sliceByteBuffer.rewind();
        //证明会覆盖原buffer
        sliceByteBuffer.put(new byte[]{'Z', 'Z'});
//        byte[] data2 = new byte[2];
//        sliceByteBuffer.get(data2);
//        System.out.println(new String(data2));

        //清理已读数据，初始化指针，转为写模式, 从未读(position <- remaining)数据开始写
        byteBuffer.compact();
        //可读可写的容量
        System.out.println("remaining:" + byteBuffer.remaining());
        //标记position位置
        byteBuffer.mark();
        byteBuffer.put(new byte[]{'K', 'L', 'E', 'D'});
        //重置到mark点, position <- mark
        byteBuffer.reset();
        //覆盖"KLED"
        byteBuffer.put(new byte[]{'M', 'A', 'R', 'K'});

        byteBuffer.flip();
        byte[] remainingBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(remainingBytes);
        System.out.println(new String(remainingBytes));

        byteBuffer.clear();
    }
}
