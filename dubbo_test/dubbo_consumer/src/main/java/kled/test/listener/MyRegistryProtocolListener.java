package kled.test.listener;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.registry.integration.RegistryProtocol;
import org.apache.dubbo.registry.integration.RegistryProtocolListener;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;

/**
 * @author: Kled
 * @version: MyRegistryProtocolListener.java, v0.1 2020-12-21 16:11 Kled
 */
@Activate
public class MyRegistryProtocolListener implements RegistryProtocolListener {
    @Override
    public void onExport(RegistryProtocol registryProtocol, Exporter<?> exporter) {
        System.out.println("MyRegistryProtocolListener -> onExport, invoker=" + exporter.getInvoker());
    }

    @Override
    public void onRefer(RegistryProtocol registryProtocol, Invoker<?> invoker) {
        System.out.println("MyRegistryProtocolListener -> onRefer, invoker=" + invoker);
    }

    @Override
    public void onDestroy() {
        System.out.println("MyRegistryProtocolListener -> onDestroy");
    }
}
