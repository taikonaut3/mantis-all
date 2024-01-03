package io.github.astro.mantis.protocol.mantis;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.Response;
import io.github.astro.mantis.protocol.ProtocolParser;
import io.github.astro.mantis.protocol.mantis.envelope.MantisRequest;
import io.github.astro.mantis.protocol.mantis.envelope.MantisResponse;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/3 17:09
 */
public class MantisProtocolParser implements ProtocolParser {

    @Override
    public Object parseBody(Request request) {
        MantisRequest mantisRequest = (MantisRequest) request.getMessage();
        return mantisRequest.getBody();
    }

    @Override
    public Object parseBody(Response response) {
        MantisResponse mantisResponse = (MantisResponse) response.getMessage();
        return mantisResponse.getBody();
    }

    @Override
    public void setBody(Request request, Object body) {
        MantisRequest mantisRequest = (MantisRequest) request.getMessage();
        mantisRequest.setBody(body);
    }

    @Override
    public void setBody(Response response, Object body) {
        MantisResponse mantisResponse = (MantisResponse) response.getMessage();
        mantisResponse.setBody(body);
    }

}
