package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.Response;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.protocol.ProtocolParser;
import io.github.astro.mantis.serialization.Converter;
import io.github.astro.mantis.serialization.Serializer;
import io.github.astro.mantis.transport.ResponseFuture;
import io.github.astro.mantis.transport.base.ChannelHandlerAdapter;
import io.github.astro.mantis.transport.channel.Channel;

import java.lang.reflect.Type;

import static io.github.astro.mantis.common.constant.KeyValues.Serialize.JSON;

public class ClientConvertChannelHandler extends ChannelHandlerAdapter {

    public ClientConvertChannelHandler(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Response response) {
            ResponseFuture future = ResponseFuture.getFuture(String.valueOf(response.getId()));
            // if timeout the future will is null
            if (future != null) {
                URL url = response.getUrl();
                Protocol protocol = ExtensionLoader.loadService(Protocol.class, url.getProtocol());
                ProtocolParser protocolParser = protocol.getParser(url);
                if (response.getCode() == Response.SUCCESS) {
                    Caller caller = future.getCallData().getCaller();
                    String serial = url.getParameter(Key.SERIALIZE);
                    if (serial.equals(JSON)) {
                        Converter converter = ExtensionLoader.loadService(Serializer.class, JSON);
                        Type returnType = future.getReturnType();
                        Object body = protocolParser.parseBody(response);
                        Object newBody = converter.convert(body, returnType);
                        protocolParser.setBody(response, newBody);
                    }
                    response.getUrl().addParameter(Key.EVENT_DISPATCHER, caller.getEventDispatcher());
                } else if (response.getCode() == Response.ERROR) {
                    // throw server handle Exception -> ChannelHandlerExceptionEventListener
                    throw new RpcException(protocolParser.parseBody(response).toString());
                }
            }
        }
    }

}
