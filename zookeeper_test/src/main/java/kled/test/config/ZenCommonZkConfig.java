package kled.test.config;

import com.zenlayer.commons.curator.CuratorConfig;
import com.zenlayer.commons.curator.CuratorInstance;
import com.zenlayer.commons.curator.CuratorInstanceImpl;
import com.zenlayer.commons.locks.LockExecutor;
import com.zenlayer.commons.locks.zk.ZookeeperLockExecutorImpl;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkImpl;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Kled
 * @version: ZenCommonZkConfig.java, v0.1 2020-11-30 14:43 Kled
 */
@Configuration
public class ZenCommonZkConfig {

    @Bean
    @ConfigurationProperties(prefix = "curator")
    public CuratorConfig curatorConfig() {
        return new CuratorConfig();
    }

    @Bean
    @ConditionalOnBean(name = "curatorConfig")
    public CuratorInstance curatorInstance(CuratorConfig curatorConfig) throws Exception {
        return new CuratorInstanceImpl(curatorConfig);
    }

    @Bean
    @ConditionalOnBean(name = "curatorInstance")
    public CuratorFramework curatorFramework(CuratorInstance curatorInstance) throws Exception {
        return curatorInstance.getCurator();
    }

    @Bean
    public LockExecutor<InterProcessMutex> zkLockExecutor(CuratorInstance curatorInstance){
        return new ZookeeperLockExecutorImpl(curatorInstance, "lock");
    }
}
