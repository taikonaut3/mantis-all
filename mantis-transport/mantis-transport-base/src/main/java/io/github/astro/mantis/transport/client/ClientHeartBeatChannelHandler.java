package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.base.HeartBeatChannelHandler;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.HeartBeatEvent;


public class ClientHeartBeatChannelHandler extends HeartBeatChannelHandler {

    public ClientHeartBeatChannelHandler() {
        addWatchEventKey(Key.ALL_IDLE_TIMES);
        addWatchEventKey(Key.WRITE_IDLE_TIMES);
    }

    @Override
    public void heartBeat(Channel channel, Object event) {
        URL url = (URL) channel.getAttribute(Key.URL);
        String eventDispatcherKey = url.getParameter(Key.EVENT_DISPATCHER, Constant.DEFAULT_EVENT_DISPATCHER);
        EventDispatcher eventDispatcher = ExtensionLoader.loadService(EventDispatcher.class, eventDispatcherKey);
        eventDispatcher.dispatchEvent(new HeartBeatEvent(channel));
    }

}
