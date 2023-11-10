package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.configuration.RemoteCaller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RemoteCallerManager extends AbstractManager<List<RemoteCaller<?>>> {

    public Collection<RemoteCaller<?>> getRemoteCallers() {
        LinkedList<RemoteCaller<?>> remoteCallers = new LinkedList<>();
        for (List<RemoteCaller<?>> value : map.values()) {
            remoteCallers.addAll(value);
        }
        return remoteCallers;
    }

    public synchronized void register(RemoteCaller<?> remoteCaller) {
        List<RemoteCaller<?>> remoteCallers = map.computeIfAbsent(remoteCaller.getRemoteApplicationName(), k -> new LinkedList<>());
        remoteCallers.add(remoteCaller);
    }

}
