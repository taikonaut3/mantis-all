package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.ProtocolType;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.invoke.Result;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.header.Header;
import io.github.astro.mantis.transport.server.Server;

import static io.github.astro.mantis.common.constant.ServiceType.Protocol.MANTIS;

@ServiceInterface(MANTIS)
public interface Protocol {

    ProtocolType getType();

    Header createHeader(URL url);

    Codec getCodec(URL url, Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo);

    Server openServer(URL url) throws NetWorkException;

    Client openClient(URL url) throws NetWorkException;

    Result send(URL url, Invocation invocation);

    Server[] getServers();

    Client[] getClients();

}