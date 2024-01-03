package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.endpoint.Endpoint;

/**
 * Network client
 */
public interface EndpointClient extends Endpoint, Client {

    /**
     * Returns the channel associated.
     *
     * @return
     */
    Channel getChannel();


    default void send(Object message){
        getChannel().send(message);
    }

}