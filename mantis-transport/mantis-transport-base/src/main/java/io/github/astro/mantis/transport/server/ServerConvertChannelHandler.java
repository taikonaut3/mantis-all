package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.*;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.serialization.Converter;
import io.github.astro.mantis.serialization.Serializer;
import io.github.astro.mantis.transport.base.ChannelHandlerAdapter;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.util.ProtocolUtil;

import static io.github.astro.mantis.common.constant.KeyValues.Serialize.JSON;

public class ServerConvertChannelHandler extends ChannelHandlerAdapter {

    public ServerConvertChannelHandler(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Request request) {
            ConfigurationManager manager = mantisApplication.getConfigurationManager();
            URL url = request.getUrl();
            DefaultCallData callData = (DefaultCallData)ProtocolUtil.parseBody(request);
            RemoteService<?> remoteService = manager.getRemoteServiceManager().get(callData.getRemoteServiceName());
            if (remoteService == null) {
                throw new SourceException("Can't find [" + callData.getRemoteServiceName() + "]remoteService");
            }
            // todo check protocol
            Caller caller = remoteService.getCaller(callData.getCallName());
            if (caller == null) {
                throw new SourceException("Can't find [" + callData.getRemoteServiceName() + "]remoteService:[" + callData.getCallName() + "] ProviderCaller");
            }
            callData.setCaller(caller);
            callData.setReturnType(caller.getMethod().getGenericReturnType());
            callData.setParameterTypes(caller.getMethod().getGenericParameterTypes());
            request.getUrl().addParameter(Key.SERVER_EVENT_DISPATCHER, caller.getEventDispatcher());
            String serial = url.getParameter(Key.SERIALIZE);
            if (serial.equals(JSON)) {
                Converter converter = ExtensionLoader.loadService(Serializer.class,JSON);
                Object[] args = converter.convert(callData.getArgs(), callData.getParameterTypes());
                callData.setArgs(args);
            }
        }

    }

}
