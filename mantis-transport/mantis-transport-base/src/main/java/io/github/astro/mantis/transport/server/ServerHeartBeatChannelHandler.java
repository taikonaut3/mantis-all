package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.base.HeartBeatChannelHandler;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.HeartBeatEvent;


public class ServerHeartBeatChannelHandler extends HeartBeatChannelHandler {

    public ServerHeartBeatChannelHandler() {
        addWatchEventKey(Key.ALL_IDLE_TIMES);
        addWatchEventKey(Key.READER_IDLE_TIMES);
    }

    @Override
    public void heartBeat(Channel channel, Object event) {
        URL url = (URL) channel.getAttribute(Key.URL);
        String eventDispatcherKey = url.getParameter(Key.SERVER_EVENT_DISPATCHER, Constant.DEFAULT_EVENT_DISPATCHER);
        EventDispatcher eventDispatcher = ExtensionLoader.loadService(EventDispatcher.class, eventDispatcherKey);
        eventDispatcher.dispatchEvent(new HeartBeatEvent(channel));
    }

}
