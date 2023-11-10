package io.github.astro.mantis.rpc.listener;

import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventListener;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.ResponseFuture;
import io.github.astro.mantis.transport.event.ChannelHandlerExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle all {@link io.github.astro.mantis.transport.channel.ChannelHandler} occur Exception
 *
 * @see io.github.astro.mantis.transport.channel.ChannelHandlerChain
 */
public class ChannelHandlerExceptionEventListener implements EventListener<ChannelHandlerExceptionEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ChannelHandlerExceptionEventListener.class);

    @Override
    public void onEvent(ChannelHandlerExceptionEvent event) {
        Envelope envelope = event.getEnvelope();
        // Handle server's All ChannelHandler Exception
        if (envelope instanceof Request request) {
            logger.error("Server: " + event.getChannelHandler().getClass().getSimpleName() + " Exception", event.getSource());
            Mode protocolMode = request.getHeader().getProtocolMode();
            Protocol protocol = ExtensionLoader.loadService(Protocol.class, protocolMode.name());
            Response response = protocol.createResponse(request.getUrl(), event.getSource().getMessage());
            response.setCode(Response.ERROR);
            response.setId(request.getId());
            event.getChannel().send(response);
        } else if (envelope instanceof Response response) {
            logger.error("Client: " + event.getChannelHandler().getClass().getSimpleName() + " Exception", event.getSource());
            ResponseFuture future = ResponseFuture.getFuture(response.getId());
            // if timeout the future will is null
            if (future != null) {
                future.completeExceptionally(event.getSource());
            }
        }
    }

}
