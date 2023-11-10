package io.github.astro.mantis.registry.zookeeper;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.RemoteUrl;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.registry.AbstractRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ZookeeperRegistry extends AbstractRegistry {

    private final String servicesDir = Constant.SERVICES_DIR;

    // 存储监听器，用于监听URL
    private final Map<String, CuratorCache> curatorCacheMap = new ConcurrentHashMap<>();

    private CuratorFramework curatorFramework;

    public ZookeeperRegistry(URL url) {
        super(url);
    }

    @Override
    protected void initClient(URL url) {
        int connectTimeout = url.getIntParameter(Key.CONNECT_TIMEOUT);
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(connectTimeout)
                .connectString(url.getAddress())
                .sessionTimeoutMs(url.getIntParameter(Key.SESSION_TIMEOUT))
                .retryPolicy(new RetryNTimes(url.getIntParameter(Key.RETRIES), url.getIntParameter(Key.RETRY_INTERVAL)))
                .build();
        curatorFramework.start();
    }

    @Override
    protected void doConnect(URL url) {
        int connectTimeout = url.getIntParameter(Key.CONNECT_TIMEOUT);
        try {
            boolean connected = curatorFramework.blockUntilConnected(connectTimeout, TimeUnit.MILLISECONDS);
            if (!connected) {
                throw new IllegalStateException("zookeeper not connected, the address is: " + url);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isConnected() {
        return curatorFramework.getZookeeperClient().isConnected();
    }

    @Override
    protected void doRegister(RemoteUrl url) {
        String path = servicesDir + GenerateUtil.generateKey(url);
        createNode(path, url.toString());
    }

    @Override
    protected URL doDiscover(CallData callData) {
        String registryPath = servicesDir + GenerateUtil.generateKey(callData);
        try {
            String urlStr = new String(curatorFramework.getData().forPath(registryPath));
            return URL.valueOf(urlStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribe(URL url) {
        String path = GenerateUtil.generateKey(url);
        CuratorCache curatorCache = curatorCacheMap.get(path);
        if (curatorCache == null) {
            curatorCache = CuratorCache.build(curatorFramework, servicesDir + path, CuratorCache.Options.DO_NOT_CLEAR_ON_CLOSE);
            curatorCache.start();
            curatorCache.listenable().addListener(new ZookeeperListener(listener));
        }
    }

    @Override
    public void unSubscribe(URL url) {
        String path = GenerateUtil.generateKey(url);
        CuratorCache curatorCache = curatorCacheMap.get(path);
        curatorCache.close();
        curatorCacheMap.remove(path);
    }

    @Override
    public void destroy() {
        curatorFramework.close();
    }

    private void createNode(String path) {
        try {
            curatorFramework.create().forPath(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createNode(String path, String data) {
        try {
            Stat stat = curatorFramework.checkExists().forPath(path);
            if (stat == null) {
                curatorFramework.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
            } else {
                curatorFramework.setData().forPath(path, data.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
