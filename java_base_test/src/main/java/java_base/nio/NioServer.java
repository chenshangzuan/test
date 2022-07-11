package java_base.nio;

import java_base.nio.reactor.Reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author Kled
 * @date 2022/4/26 10:12 AM
 */
public class NioServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        //simpleServerSocketChannel();
        reactorMode();
    }

    public static void reactorMode() throws IOException, InterruptedException {
        Thread thread = new Thread(new Reactor(8888));
        thread.start();
        System.out.println("Reactor ServerSocketChannel start, listen to 8888 port");
//        Thread.sleep(100000);
        thread.join();
    }

    public static void simpleServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        System.out.println("ServerSocketChannel start, listen to 8888 port");

        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        socketChannel.read(byteBuffer);

        byteBuffer.flip();
        byte[] data = new byte[byteBuffer.remaining()];
        byteBuffer.get(data);
        System.out.println("NioServer -> get message:" + new String(data));

        byteBuffer.clear();
        byteBuffer.put(new byte[]{'O', 'K'});
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        serverSocketChannel.close();
    }
}
