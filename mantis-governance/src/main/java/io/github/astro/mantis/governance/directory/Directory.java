package io.github.astro.mantis.governance.directory;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;

import java.util.List;

import static io.github.astro.mantis.common.constant.KeyValues.Directory.DEFAULT;

@ServiceInterface(DEFAULT)
public interface Directory {

    List<URL> list(CallData callData, URL... urls);

    /**
     * destroy cache
     */
    void destroy();

}
