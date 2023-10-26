package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.configuration.RemoteCaller;

import java.util.Collection;

public class RemoteCallerManager extends AbstractManager<RemoteCaller<?>> {

    public Collection<RemoteCaller<?>> getRemoteCallers() {
        return map.values();
    }
}
