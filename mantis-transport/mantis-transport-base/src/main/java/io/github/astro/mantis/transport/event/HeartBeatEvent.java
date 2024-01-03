package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.event.AbstractEvent;
import io.github.astro.mantis.transport.channel.Channel;

public class HeartBeatEvent extends AbstractEvent<Channel> {


    public HeartBeatEvent(Channel channel) {
        super(channel);
    }



}
