package localTest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.stream.Stream;

/**
 * @author chenpc
 * @version $Id: localTest.TestEncode.java, v 0.1 2018-05-22 16:28:47 chenpc Exp $
 */
public class TestEncode {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String s = "可烈";
        Charset charset = Charset.forName("UTF-8");
        //System.out.println(charset.encode(s)); return ByteBuffer
        byte[] bs = s.getBytes("UTF-8");
        Stream.of(s.getBytes(charset)).forEach(System.out::println);

        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString("vap".getBytes());
        System.out.println(encode);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update("test".getBytes());
        System.out.println(md5.digest());
    }
}
