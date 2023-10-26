package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.ResponseCode;
import io.github.astro.mantis.common.exception.InvokeServiceException;
import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.InvokerProcessor;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.executor.MantisThreadPool;
import io.github.astro.mantis.configuration.invoke.DefaultInvocation;
import io.github.astro.mantis.event.EventType;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestEventListener extends EnvelopeEventListener<RequestEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RequestEventListener.class);

    public RequestEventListener(MantisBootStrap mantisBootStrap) {
        super(MantisThreadPool.getDefaultIOExecutor("HandleRequest"), mantisBootStrap);
        super.eventTypes.add(EventType.Request);
    }

    @Override
    protected void handEnvelopeEvent(RequestEvent event) {
        Request request = event.getSource();
        DefaultInvocation invocation = (DefaultInvocation) request.getBody();
        Channel channel = event.getChannel();
        Header header = request.getHeader();
        try {
            Invoker invoker = invocation.getInvoker();
            if (invoker == null) {
                throw new SourceException("Can't find [" + invocation.getExportName() + "]Exporter:[" + invocation.getMethodKey() + "] ProviderInvoker ");
            }
            String serial = invoker.getSerialize();
            header.addExtendData(Key.SERIALIZE, serial);
            InvokerProcessor[] processors = invoker.getProcessors();
            for (InvokerProcessor processor : processors) {
                processor.processBeforeInvoke(invoker, invocation, event.getUrl());
            }
            Object result = invoker.invoke(invocation);
            for (InvokerProcessor processor : processors) {
                processor.processAfterInvoke(invoker, invocation, event.getUrl());
            }
            header.addExtendData(Key.RESPONSE_CODE, String.valueOf(ResponseCode.SUCCESS));
            Response response = new Response(header, result);
            channel.send(response);
        } catch (Exception e) {
            String message = "Invoke [" + invocation.getExportName() + "]Exporter:[" + invocation.getMethodKey() + "]ProviderInvoker Error: " + e.getMessage();
            header.addExtendData(Key.RESPONSE_CODE, String.valueOf(ResponseCode.ERROR));
            Response response = new Response(header, message);
            channel.send(response);
            throw new InvokeServiceException(message, e);
        }
    }

    @Override
    public Class<RequestEvent> getEventType() {
        return RequestEvent.class;
    }
}
