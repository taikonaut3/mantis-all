package io.github.astro.mantis.rpc.listener;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.event.EventListener;
import io.github.astro.mantis.transport.ResponseFuture;
import io.github.astro.mantis.transport.event.ServerHandlerExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/5 15:49
 */
public class ServerHandlerExceptionListener implements EventListener<ServerHandlerExceptionEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandlerExceptionListener.class);

    @Override
    public void onEvent(ServerHandlerExceptionEvent event) {
        URL url = (URL) event.getChannel().getAttribute(Key.URL);
        Throwable cause = event.getSource();
        logger.error("Client: {} Exception: {}", event.getChannel(), cause.getMessage());
        ResponseFuture future = ResponseFuture.getFuture(url.getParameter(Key.UNIQUE_ID));
        // if timeout the future will is null
        if (future != null) {
            future.completeExceptionally(event.getSource());
        }
    }

}
