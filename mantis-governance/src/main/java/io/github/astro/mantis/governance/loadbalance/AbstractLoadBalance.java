package io.github.astro.mantis.governance.loadbalance;

import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public URL select(List<URL> urls, Invocation invocation) {
        if (urls.isEmpty()) {
            throw new SourceException("Not available Service Urls");
        }
        if (urls.size() == 1) {
            return urls.get(0);
        }
        return doSelect(urls, invocation);
    }

    protected abstract URL doSelect(List<URL> urls, Invocation invocation);
}
