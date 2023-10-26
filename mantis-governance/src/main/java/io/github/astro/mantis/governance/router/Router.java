package io.github.astro.mantis.governance.router;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.util.List;

import static io.github.astro.mantis.common.constant.ServiceType.Router.WEIGHT;

@ServiceInterface(WEIGHT)
public interface Router {

    List<URL> route(List<URL> urls, Invocation invocation);
}
