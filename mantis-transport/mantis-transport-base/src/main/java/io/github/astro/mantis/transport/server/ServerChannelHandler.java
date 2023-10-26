package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.constant.SerializerType;
import io.github.astro.mantis.configuration.Exporter;
import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.invoke.DefaultInvocation;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import io.github.astro.mantis.serialization.Converter;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.EnvelopeChannelHandler;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.RequestEvent;
import io.github.astro.mantis.transport.event.RequestEventListener;

public final class ServerChannelHandler extends EnvelopeChannelHandler {

    public ServerChannelHandler(MantisBootStrap mantisBootStrap) {
        super(mantisBootStrap);
        eventDispatcher.addListener(RequestEvent.class, new RequestEventListener(mantisBootStrap));
    }

    @Override
    protected void convert(Envelope envelope) {
        if (envelope instanceof Request request) {
            ConfigurationManager manager = mantisBootStrap.getConfigurationManager();
            DefaultInvocation invocation = (DefaultInvocation) request.getBody();
            Exporter<?> exporter = manager.getExporterManager().get(invocation.getExportName());
            if (exporter != null) {
                Invoker invoker = exporter.getInvoker(invocation.getMethodKey());
                invocation.setInvoker(invoker);
                invocation.setReturnType(invoker.getMethod().getGenericReturnType());
                invocation.setParameterTypes(invoker.getMethod().getGenericParameterTypes());
            }
            if (request.getHeader().getSerializerType() == SerializerType.JSON) {
                Converter converter = request.getHeader().getSerializer();
                Object[] args = converter.convert(invocation.getArgs(), invocation.getParameterTypes());
                invocation.setArgs(args);
            }
        }
    }

    @Override
    protected void receivedEnvelope(Channel channel, Envelope envelope) {
        if (envelope instanceof Request request) {
            eventDispatcher.dispatchEvent(new RequestEvent(request, channel));
        }
    }
}
