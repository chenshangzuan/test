package java_base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author kled
 * @version $Id: TestSocket.java, v 0.1 2018-08-28 20:02:43 kled Exp $
 */
public class TestSocket {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",1111);
        System.out.println("InetAddress" + socket.getInetAddress());
        System.out.println("远程主机地址：" + socket.getRemoteSocketAddress());
        OutputStream outToServer = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        out.writeUTF("Hello from " + socket.getLocalSocketAddress());
        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        System.out.println("服务器响应： " + in.readUTF());
        socket.close();
    }
}
