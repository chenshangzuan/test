package kled.test.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author: Kled
 * @version: ProviderFilter.java, v0.1 2020-11-27 15:51 Kled
 */
@Activate(group = {CommonConstants.PROVIDER})
public class ProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("ProviderFilter, method: " + invocation.getMethodName());
        return invoker.invoke(invocation);
    }
}
