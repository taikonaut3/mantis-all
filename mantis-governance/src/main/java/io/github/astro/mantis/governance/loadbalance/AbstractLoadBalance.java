package io.github.astro.mantis.governance.loadbalance;

import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public URL select(List<URL> urls, CallData callData) {
        if (urls.isEmpty()) {
            throw new SourceException("Not available Service Urls");
        }
        if (urls.size() == 1) {
            return urls.get(0);
        }
        return doSelect(urls, callData);
    }

    protected abstract URL doSelect(List<URL> urls, CallData callData);

}
