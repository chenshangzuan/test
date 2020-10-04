package localTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kled
 * @version $Id: TestServerSocket.java, v 0.1 2018-08-28 20:05:09 kled Exp $
 */
public class TestServerSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1111);
        System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
        Socket server = serverSocket.accept();
        System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
        DataInputStream in = new DataInputStream(server.getInputStream());
        System.out.println(in.readUTF());
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        out.writeUTF("谢谢连接我：" + server.getLocalSocketAddress() + "\nGoodbye!");
        server.close();
    }
}
