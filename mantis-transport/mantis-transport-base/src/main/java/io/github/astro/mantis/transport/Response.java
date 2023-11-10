package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.transport.header.Header;

public class Response extends AbstractEnvelope {

    public static final byte SUCCESS = 0, ERROR = -1, TIMEOUT = 3;

    private byte code;

    public Response(Header header, Object body) {
        super(header, body);
    }

    public Response() {
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
        getHeader().addExtendData(Key.RESPONSE_CODE, String.valueOf(code));
    }

    @Override
    public void setHeader(Header header) {
        super.setHeader(header);
        if (!StringUtils.isBlank(header.getExtendData(Key.RESPONSE_CODE))) {
            this.code = Byte.parseByte(header.getExtendData(Key.RESPONSE_CODE));
        }
    }

}
