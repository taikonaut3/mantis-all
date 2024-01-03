package io.github.astro.mantis.protocol.mantis.envelope;

import io.github.astro.mantis.protocol.mantis.header.Header;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/3 13:29
 */
public class MantisRequest extends AbstractMantisEnvelope {


    public MantisRequest(){

    }
    public MantisRequest(Header header, Object body) {
        super(header, body);
    }

}
