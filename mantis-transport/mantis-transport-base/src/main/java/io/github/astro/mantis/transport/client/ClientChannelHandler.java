package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.constant.ResponseCode;
import io.github.astro.mantis.common.constant.SerializerType;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.serialization.Converter;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.EnvelopeChannelHandler;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.ResponseEvent;
import io.github.astro.mantis.transport.event.ResponseEventListener;
import io.github.astro.mantis.transport.event.ResponseFuture;

import java.lang.reflect.Type;

public final class ClientChannelHandler extends EnvelopeChannelHandler {

    public ClientChannelHandler(MantisBootStrap mantisBootStrap) {
        super(mantisBootStrap);
        eventDispatcher.addListener(ResponseEvent.class, new ResponseEventListener(mantisBootStrap));
    }

    @Override
    protected void convert(Envelope envelope) {
        if (envelope instanceof Response response && response.getCode() == ResponseCode.SUCCESS) {
            if (response.getHeader().getSerializerType() == SerializerType.JSON) {
                Converter converter = response.getHeader().getSerializer();
                Type returnType = ResponseFuture.getFuture(response.getId()).getReturnType();
                Object body = converter.convert(response.getBody(), returnType);
                response.setBody(body);
            }
        }
    }

    @Override
    protected void receivedEnvelope(Channel channel, Envelope envelope) {
        if (envelope instanceof Response response) {
            eventDispatcher.dispatchEvent(new ResponseEvent(response));
        }
    }
}
