package io.github.astro.mantis.governance.loadbalance;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;

import java.util.List;

import static io.github.astro.mantis.common.constant.KeyValues.LoadBalance.RANDOM;

@ServiceInterface(RANDOM)
public interface LoadBalance {

    URL select(List<URL> urls, CallData callData);

}
