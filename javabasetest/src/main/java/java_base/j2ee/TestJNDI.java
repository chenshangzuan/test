package java_base.j2ee;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

import javax.naming.*;
import java.util.Hashtable;

/**
 * @author chenpc
 * @version $Id: java_base.j2ee.TestJNDI.java, v 0.1 2018-03-21 14:48:25 chenpc Exp $
 */
public class TestJNDI {
    //测试文件系统目录
    static final String CONTEXT_FACTORY = "com.sun.jndi.fscontext.FSContextFactory";
    final static String PROVIDER_URL = "file:/";

    public static void main(String[] args) throws Exception {
        //Q: 无参初始化时，J2EE容器需要配置默认的ContextFactory ?
//        Context localContext = new InitialContext();
//        localContext.lookup("");
//        localContext.bind("test", "kled");
//        System.out.println(localContext.lookup("test"));

        System.out.println("********************* fscontext *************");
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        Context ctx = new InitialContext(env);

        Context myHome = (Context) ctx.lookup("/Users/kled");
        System.out.println(myHome.lookup("git"));

        System.out.println("-------------------------------------------");
        NamingEnumeration<NameClassPair> list = ctx.list("/Users/kled");
        while (list.hasMoreElements()) {
            NameClassPair nameClassPair = (NameClassPair) list.nextElement();
            System.out.println(nameClassPair);
        }
//        NamingEnumeration<Binding> listBindings = ctx.listBindings("/Users/Shared");
//        while (listBindings.hasMoreElements()) {
//            Binding binding = listBindings.nextElement();
//            System.out.println(binding);
//        }
        System.out.println("-------------------------------------------");
        //System.out.println(ctx.lookup("/apache-tomcat-7.0.72/webapps").getClass());
        //System.out.println(ctx.lookup("/apache-tomcat-7.0.72/NOTICE").getClass());
    }
}

