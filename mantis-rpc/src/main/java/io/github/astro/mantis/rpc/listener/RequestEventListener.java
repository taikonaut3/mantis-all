package io.github.astro.mantis.rpc.listener;

import io.github.astro.mantis.Response;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.executor.MantisThreadPool;
import io.github.astro.mantis.rpc.MantisServerInvoker;
import io.github.astro.mantis.transport.event.EnvelopeEventListener;
import io.github.astro.mantis.transport.event.RequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestEventListener extends EnvelopeEventListener<RequestEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RequestEventListener.class);

    public RequestEventListener(MantisApplication mantisApplication) {
        super(MantisThreadPool.getDefaultIOExecutor("HandleRequest"), mantisApplication);
    }

    @Override
    protected void handEnvelopeEvent(RequestEvent event) {
        logger.debug("Received Event({})", event.getClass().getSimpleName());
        Response response = new MantisServerInvoker(event).invoke();
        if (response != null) {
            event.getChannel().send(response);
        }
    }

}
