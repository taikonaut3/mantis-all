package io.github.astro.mantis.transport.http;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.client.ClientTransport;
import io.github.astro.mantis.transport.client.EndpointClient;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/6 8:55
 */
public class HttpClientTransport implements ClientTransport {

    private HttpEndpointClient client;

    public HttpClientTransport(){

    }


    @Override
    public Client connect(URL url, Codec codec) throws NetWorkException {
        return client;
    }

    @Override
    public Client[] getClients() {
        return new EndpointClient[0];
    }

    @Override
    public void setClientChannelHandler(ChannelHandler channelHandler) {

    }

    @Override
    public ChannelHandler getClientChannelHandler() {
        return null;
    }
}
