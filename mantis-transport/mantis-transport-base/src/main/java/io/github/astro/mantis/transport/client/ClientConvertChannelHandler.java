package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.serialization.Converter;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.ResponseFuture;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerAdapter;

import java.lang.reflect.Type;

import static io.github.astro.mantis.common.constant.KeyValues.Serialize.JSON;

public class ClientConvertChannelHandler extends DefaultChannelHandlerAdapter {

    public ClientConvertChannelHandler(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Response response) {
            ResponseFuture future = ResponseFuture.getFuture(response.getId());
            // if timeout the future will is null
            if (future != null) {
                if (response.getCode() == Response.SUCCESS) {
                    Caller caller = future.getCallData().getCaller();
                    if (response.getHeader().getSerializerMode().name().equals(JSON)) {
                        Converter converter = response.getHeader().getSerializer();
                        Type returnType = future.getReturnType();
                        Object body = converter.convert(response.getBody(), returnType);
                        response.setBody(body);
                    }
                    response.getUrl().addParameter(Key.EVENT_DISPATCHER, caller.getEventDispatcher());
                } else if (response.getCode() == Response.ERROR) {
                    // throw server handle Exception -> ChannelHandlerExceptionEventListener
                    throw new RpcException(response.getBody().toString());
                }
            }
        }
    }

}
