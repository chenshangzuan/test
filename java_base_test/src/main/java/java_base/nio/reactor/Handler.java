package java_base.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author Kled
 * @date 2022/4/26 3:58 PM
 */
public class Handler implements Runnable{
    private final SocketChannel socketChannel;

    private final Selector selector;

    private final SelectionKey selectionKey;

    private final ByteBuffer inputByteBuffer = ByteBuffer.allocate(1024);

    private final ByteBuffer outputByteBuffer = ByteBuffer.allocate(1024);

    public Handler(SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);

        selector = Selector.open();
        selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        selector.wakeup();
    }

    //处理客户点非I/O连接请求
    @Override
    public void run() {
        while (selector.isOpen() && socketChannel.isOpen()){
            try {
                selector.select();
                //selector.selectNow(); //不管什么就绪都直接返回，无通道返回0
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    if(key.isReadable()){
                        read(key);
                    }
                    if(key.isWritable()){
                        write(key);
                    }
                }
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void write(SelectionKey key) throws IOException {
        outputByteBuffer.flip();
        socketChannel.write(outputByteBuffer);
        outputByteBuffer.clear();
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        socketChannel.read(inputByteBuffer);
        if(inputByteBuffer.position() == 0){
            return;
        }

        inputByteBuffer.flip();
        byte[] data = new byte[inputByteBuffer.remaining()];
        inputByteBuffer.get(data);
        System.out.println("receive:" + new String(data, StandardCharsets.UTF_8));
        inputByteBuffer.clear();

        outputByteBuffer.put("hello client".getBytes());
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }
}
