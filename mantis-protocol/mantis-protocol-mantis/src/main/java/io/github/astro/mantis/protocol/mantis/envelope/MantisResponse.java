package io.github.astro.mantis.protocol.mantis.envelope;

import io.github.astro.mantis.protocol.mantis.header.Header;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/3 13:29
 */
public class MantisResponse extends AbstractMantisEnvelope {

    public MantisResponse(){

    }
    public MantisResponse(Header header, Object payload) {
        super(header,payload);
    }

}
