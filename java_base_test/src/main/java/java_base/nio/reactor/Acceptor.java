package java_base.nio.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kled
 * @date 2022/4/26 4:04 PM
 */
public class Acceptor{

    private final ServerSocketChannel serverSocketChannel;

    private final ExecutorService subReactor = Executors.newFixedThreadPool(10);

    public Acceptor(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;
    }

    //处理客户点非I/O连接请求
    public void accept() throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        subReactor.execute(new Handler(socketChannel));
    }
}
