package io.github.astro.mantis.governance.router;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;

import java.util.List;

import static io.github.astro.mantis.common.constant.KeyValues.Router.WEIGHT;

@ServiceInterface(WEIGHT)
public interface Router {

    List<URL> route(List<URL> urls, CallData callData);

}
