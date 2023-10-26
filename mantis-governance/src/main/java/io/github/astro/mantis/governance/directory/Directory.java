package io.github.astro.mantis.governance.directory;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.util.List;

import static io.github.astro.mantis.common.constant.ServiceType.Directory.DEFAULT;

@ServiceInterface(DEFAULT)
public interface Directory {

    List<URL> list(Invocation invocation, URL... urls);

    /**
     * 销毁目录
     */
    void destroy();
}
