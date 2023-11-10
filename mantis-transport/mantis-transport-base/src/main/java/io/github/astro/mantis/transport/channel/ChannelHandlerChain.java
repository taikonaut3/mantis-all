package io.github.astro.mantis.transport.channel;

/**
 * Connect the ChannelHandler in order
 */
public interface ChannelHandlerChain extends ChannelHandler {

    /**
     * Just the addition of the Inbound ChannelHandler
     *
     * @param channelHandler
     */
    ChannelHandlerChain addLast(ChannelHandler channelHandler);

    /**
     * Gets all Inbound ChannelHandler
     *
     * @return
     */
    ChannelHandler[] getChannelHandlers();

    /**
     * Gets all access Current ChannelHandlerChain's Channel
     *
     * @return
     */
    Channel[] getChannels();

}
