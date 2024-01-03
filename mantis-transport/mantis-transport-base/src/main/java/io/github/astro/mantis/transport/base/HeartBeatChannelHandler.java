package io.github.astro.mantis.transport.base;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.transport.channel.Channel;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Follow-up consideration for extensions
 */
public class HeartBeatChannelHandler extends ChannelHandlerAdapter {

    private final Set<String> watchEventKeys = new HashSet<>();

    @Override
    public void received(Channel channel, Object message) throws NetWorkException {
        for (String watchEventKey : watchEventKeys) {
            channel.setAttribute(watchEventKey, new AtomicInteger(0));
        }
    }

    @Override
    public void heartBeat(Channel channel, Object event) {
        super.heartBeat(channel, event);
    }

    public HeartBeatChannelHandler addWatchEventKey(String key) {
        watchEventKeys.add(key);
        return this;
    }

}
