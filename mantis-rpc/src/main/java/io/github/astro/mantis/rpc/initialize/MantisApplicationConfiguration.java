package io.github.astro.mantis.rpc.initialize;

import io.github.astro.mantis.configuration.ConfigurationMantisApplication;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.client.ClientTransport;
import io.github.astro.mantis.transport.server.ServerTransport;

/**
 * Registry Global Default Listeners
 */
@ServiceProvider
public class MantisApplicationConfiguration implements ConfigurationMantisApplication {

    @Override
    public void configureInit(MantisApplication application) {
        ExtensionLoader.addListener(ClientTransport.class, new InitializeClientTransportListener(application));
        ExtensionLoader.addListener(ServerTransport.class, new InitializeServerTransportListener(application));
        ExtensionLoader.addListener(EventDispatcher.class, new InitializeEventDispatcherListener(application));
    }

}
