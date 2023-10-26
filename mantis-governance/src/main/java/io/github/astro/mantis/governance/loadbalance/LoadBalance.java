package io.github.astro.mantis.governance.loadbalance;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.util.List;

import static io.github.astro.mantis.common.constant.ServiceType.LoadBalance.RANDOM;

@ServiceInterface(RANDOM)
public interface LoadBalance {

    URL select(List<URL> urls, Invocation invocation);
}
