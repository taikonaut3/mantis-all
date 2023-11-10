package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.DefaultCallData;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.RemoteService;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import io.github.astro.mantis.serialization.Converter;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerAdapter;

import static io.github.astro.mantis.common.constant.KeyValues.Serialize.JSON;

public class ServerConvertChannelHandler extends DefaultChannelHandlerAdapter {

    public ServerConvertChannelHandler(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Request request) {
            ConfigurationManager manager = mantisApplication.getConfigurationManager();
            DefaultCallData callData = (DefaultCallData) request.getBody();
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
            if (request.getHeader().getSerializerMode().name().equals(JSON)) {
                Converter converter = request.getHeader().getSerializer();
                Object[] args = converter.convert(callData.getArgs(), callData.getParameterTypes());
                callData.setArgs(args);
            }
        }

    }

}
