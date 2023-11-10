package io.github.astro.mantis.rpc.initialize;

import io.github.astro.mantis.configuration.ConfigurationMantisApplication;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.transport.Transporter;

/**
 * Registry Global Default Listeners
 */
@ServiceProvider
public class MantisApplicationConfiguration implements ConfigurationMantisApplication {

    @Override
    public void configureInit(MantisApplication application) {
        ExtensionLoader.addListener(Transporter.class, new InitializeTransportListener(application));
    }

}
