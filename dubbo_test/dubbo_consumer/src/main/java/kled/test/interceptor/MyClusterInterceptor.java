package kled.test.interceptor;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.cluster.interceptor.ClusterInterceptor;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;

/**
 * @author: Kled
 * @version: MyClusterInterceptor.java, v0.1 2020-12-21 15:58 Kled
 */
@Activate
public class MyClusterInterceptor implements ClusterInterceptor {

    @Override
    public void before(AbstractClusterInvoker<?> clusterInvoker, Invocation invocation) {
        System.out.println("MyClusterInterceptor -> before method=" + invocation.getMethodName());
    }

    @Override
    public void after(AbstractClusterInvoker<?> clusterInvoker, Invocation invocation) {
        System.out.println("MyClusterInterceptor -> after method=" + invocation.getMethodName());
    }
}
