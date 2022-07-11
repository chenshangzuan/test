package java_base.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kled
 * @date 2022/4/26 3:57 PM
 */
public class Reactor implements Runnable {

    private final Selector mainSelector;

    public Reactor(Integer serverPort) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverPort));
        serverSocketChannel.configureBlocking(false);

        mainSelector = Selector.open();
        serverSocketChannel.register(mainSelector, SelectionKey.OP_ACCEPT, new Acceptor(serverSocketChannel));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                mainSelector.select();
                Set<SelectionKey> selectionKeys = mainSelector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    Acceptor acceptor = (Acceptor) selectionKey.attachment();
                    acceptor.accept();
                }
                selectionKeys.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
