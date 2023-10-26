package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.common.constant.ResponseCode;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.executor.MantisThreadPool;
import io.github.astro.mantis.event.EventType;
import io.github.astro.mantis.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseEventListener extends EnvelopeEventListener<ResponseEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ResponseEventListener.class);

    public ResponseEventListener(MantisBootStrap mantisBootStrap) {
        super(MantisThreadPool.getDefaultCPUExecutor("HandleResponse"), mantisBootStrap);
        super.eventTypes.add(EventType.Response);
    }

    @Override
    protected void handEnvelopeEvent(ResponseEvent event) {
        ResponseFuture future = ResponseFuture.getFuture(event.getId());
        if (future != null) {
            Response response = event.getSource();
            if (response.getCode() == ResponseCode.ERROR) {
                RpcException rpcException = new RpcException(response.getBody().toString());
                future.completeExceptionally(rpcException);
            } else {
                future.complete(response.getBody());
            }
            future.delete();
        } else {
            logger.error("ResponseFuture({}) Can't exist", event.getId());
        }
    }

    @Override
    public Class<ResponseEvent> getEventType() {
        return ResponseEvent.class;
    }
}
