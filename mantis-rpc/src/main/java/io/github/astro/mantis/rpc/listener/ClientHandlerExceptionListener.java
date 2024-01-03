package io.github.astro.mantis.rpc.listener;

import io.github.astro.mantis.Response;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventListener;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.transport.event.ClientHandlerExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/5 15:49
 */
public class ClientHandlerExceptionListener implements EventListener<ClientHandlerExceptionEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandlerExceptionListener.class);

    @Override
    public void onEvent(ClientHandlerExceptionEvent event) {
        URL url = (URL) event.getChannel().getAttribute(Key.URL);
        Throwable cause = event.getSource();
        logger.error("Server: {} Exception: {}", event.getChannel(), cause.getMessage());
        String protocolKey = url.getProtocol();
        Protocol protocol = ExtensionLoader.loadService(Protocol.class, protocolKey);
        Response response = protocol.createResponse(url, cause.getMessage());
        response.setCode(Response.ERROR);
        response.setId(Long.valueOf(url.getParameter(Key.UNIQUE_ID)));
        event.getChannel().send(response);
    }

}
