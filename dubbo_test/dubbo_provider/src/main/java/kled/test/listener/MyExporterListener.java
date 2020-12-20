package kled.test.listener;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author: Kled
 * @version: MyExporterListener.java, v0.1 2020-12-20 22:01 Kled
 */
@Activate(group = {CommonConstants.EXPORTER_LISTENER_KEY})
public class MyExporterListener implements ExporterListener {
    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        System.out.println("MyExporterListener, export service=" + exporter.getInvoker().getInterface().getCanonicalName());
    }

    @Override
    public void unexported(Exporter<?> exporter) {
        System.out.println("MyExporterListener, unexport service=" + exporter.getInvoker().getInterface().getCanonicalName());

    }
}
