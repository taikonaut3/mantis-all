package io.github.astro.mantis.protocol;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.Response;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/3 17:03
 */
public interface ProtocolParser {


    Object parseBody(Request request);

    Object parseBody(Response response);

    void setBody(Request request,Object body);

    void setBody(Response response,Object body);

}
