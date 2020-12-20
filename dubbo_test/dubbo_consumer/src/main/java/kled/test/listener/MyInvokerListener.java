package kled.test.listener;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author: Kled
 * @version: MyInvokerListener.java, v0.1 2020-12-20 22:01 Kled
 */
@Activate(group = {CommonConstants.INVOKER_LISTENER_KEY})
public class MyInvokerListener implements InvokerListener {

    @Override
    public void referred(Invoker<?> invoker) throws RpcException {
        System.out.println("MyInvokerListener, refer service=" + invoker.getInterface().getCanonicalName());

    }

    @Override
    public void destroyed(Invoker<?> invoker) {
        System.out.println("MyInvokerListener, destroy service=" + invoker.getInterface().getCanonicalName());
    }
}
