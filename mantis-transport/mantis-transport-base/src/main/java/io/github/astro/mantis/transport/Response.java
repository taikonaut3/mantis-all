package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.transport.header.Header;

import java.io.Serial;

public class Response extends AbstractEnvelope {

    @Serial
    private static final long serialVersionUID = 1L;
    private byte code;

    public Response(Header header, Object body) {
        super(header, body);
        this.code = Byte.parseByte(header.getExtendData(Key.RESPONSE_CODE));
    }

    public Response() {
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

}
