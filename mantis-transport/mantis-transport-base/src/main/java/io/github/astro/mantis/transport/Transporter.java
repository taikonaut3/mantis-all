package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.server.Server;

import static io.github.astro.mantis.common.constant.ServiceType.Transport.NETTY;

@ServiceInterface(NETTY)
public interface Transporter {

    Server bind(URL url, Codec codec) throws NetWorkException;

    Client connect(URL url, Codec codec) throws NetWorkException;
}