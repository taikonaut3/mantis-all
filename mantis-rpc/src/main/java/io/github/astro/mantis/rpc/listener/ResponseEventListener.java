package io.github.astro.mantis.rpc.listener;

import io.github.astro.mantis.Response;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.executor.MantisThreadPool;
import io.github.astro.mantis.transport.ResponseFuture;
import io.github.astro.mantis.transport.event.EnvelopeEventListener;
import io.github.astro.mantis.transport.event.ResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseEventListener extends EnvelopeEventListener<ResponseEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ResponseEventListener.class);

    public ResponseEventListener(MantisApplication mantisApplication) {
        super(MantisThreadPool.getDefaultCPUExecutor("HandleResponse"), mantisApplication);
    }

    @Override
    protected void handEnvelopeEvent(ResponseEvent event) {
        logger.debug("Received Event({})", event.getClass().getSimpleName());
        String id = String.valueOf(event.getSource().getId());
        ResponseFuture future = ResponseFuture.getFuture(id);
        if (future != null) {
            Response response = event.getSource();
            future.complete(event.getBody());
        } else {
            logger.error("ResponseFuture({}) Can't exist", id);
        }
    }

}
