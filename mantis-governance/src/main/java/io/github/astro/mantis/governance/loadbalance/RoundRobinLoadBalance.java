package io.github.astro.mantis.governance.loadbalance;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.util.List;

import static io.github.astro.mantis.common.constant.ServiceType.LoadBalance.ROUND_ROBIN;

/**
 * "轮询"负载均衡策略:
 * 按照顺序将请求依次分配给每个服务器
 * 每次请求都会按照服务器列表的顺序逐个分配，当到达列表末尾时会重新从头开始
 * 这种策略适用于服务器性能相当的情况
 */
@ServiceProvider(ROUND_ROBIN)
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    private int index = 0;

    @Override
    protected URL doSelect(List<URL> urls, Invocation invocation) {
        if (index == urls.size()) {
            index = 0;
        }
        return urls.get(index++);
    }
}
