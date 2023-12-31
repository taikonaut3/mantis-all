package io.github.astro.mantis.governance.loadbalance;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;

import java.util.List;
import java.util.Random;

import static io.github.astro.mantis.common.constant.KeyValues.LoadBalance.RANDOM;

/**
 * "随机"负载均衡策略:
 * 随机地将请求分配到服务器集群中的任意一台服务器
 * 这种策略简单快速，但可能会导致服务器负载不均衡
 */
@ServiceProvider(RANDOM)
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected URL doSelect(List<URL> urls, CallData callData) {
        Random random = new Random();
        int index = random.nextInt(urls.size());
        return urls.get(index);
    }

}
