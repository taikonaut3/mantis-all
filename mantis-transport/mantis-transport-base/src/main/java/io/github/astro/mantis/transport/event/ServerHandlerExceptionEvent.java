package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.transport.channel.Channel;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/5 15:50
 */
public class ServerHandlerExceptionEvent extends ChannelHandlerExceptionEvent{

    public ServerHandlerExceptionEvent(Channel channel, Throwable cause) {
        super(channel, cause);
    }

}
