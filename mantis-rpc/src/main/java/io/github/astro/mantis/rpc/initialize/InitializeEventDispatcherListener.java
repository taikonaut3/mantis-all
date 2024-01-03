package io.github.astro.mantis.rpc.initialize;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.LoadedListener;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.rpc.listener.*;
import io.github.astro.mantis.transport.event.*;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/5 14:22
 */
public class InitializeEventDispatcherListener implements LoadedListener<EventDispatcher> {

    private final MantisApplication application;

    public InitializeEventDispatcherListener(MantisApplication application) {
        this.application = application;
    }

    @Override
    public void listen(EventDispatcher eventDispatcher) {
        eventDispatcher.addListener(HeartBeatEvent.class, new HeartBeatEventListener());
        eventDispatcher.addListener(RequestEvent.class, new RequestEventListener(application));
        eventDispatcher.addListener(ResponseEvent.class, new ResponseEventListener(application));
        eventDispatcher.addListener(ClientHandlerExceptionEvent.class, new ClientHandlerExceptionListener());
        eventDispatcher.addListener(ServerHandlerExceptionEvent.class, new ServerHandlerExceptionListener());
    }

}
