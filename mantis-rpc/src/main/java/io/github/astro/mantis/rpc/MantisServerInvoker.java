package io.github.astro.mantis.rpc;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.DateUtils;
import io.github.astro.mantis.configuration.CallInterceptor;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.DefaultCallData;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class MantisServerInvoker {

    private static final Logger logger = LoggerFactory.getLogger(MantisServerInvoker.class);

    private final Request request;

    public MantisServerInvoker(Request request) {
        this.request = request;
    }

    public Response invoke() {
        DefaultCallData callData = (DefaultCallData) request.getBody();
        Caller caller = callData.getCaller();
        Response response = null;
        try {
            boolean oneway = request.getUrl().getBooleanParameter(Key.IS_ONEWAY);
            if (oneway) {
                List<CallInterceptor> interceptors = caller.getCallInterceptors();
                // todo 超时处理
                Object result = caller.call(callData);
                response = createResponse(caller, request, result, Response.SUCCESS);
            } else {
                long timeout = request.getUrl().getLongParameter(Key.TIMEOUT);
                String timestamp = request.getUrl().getParameter(Key.TIMESTAMP);
                LocalDateTime localDateTime = DateUtils.parse(timestamp, DateUtils.COMPACT_FORMAT);
                long margin = Duration.between(localDateTime, LocalDateTime.now()).toMillis();
                if (margin < timeout) {
                    List<CallInterceptor> interceptors = caller.getCallInterceptors();
                    Object result = caller.call(callData);
                    long invokeAfterMargin = Duration.between(localDateTime, LocalDateTime.now()).toMillis();
                    if (invokeAfterMargin < timeout) {
                        response = createResponse(caller, request, result, Response.SUCCESS);
                    }
                }
            }
        } catch (Exception e) {
            String errorMessage = "Invoke [" + callData.getRemoteServiceName() + "]remoteService:[" + callData.getCallName() + "]ProviderCaller Error \n" + e.getMessage();
            logger.error(errorMessage, e);
            response = createResponse(caller, request, errorMessage, Response.ERROR);
        }
        return response;
    }

    private Response createResponse(Caller caller, Request request, Object body, byte code) {
        Protocol protocol = ExtensionLoader.loadService(Protocol.class, request.getUrl().getProtocol());
        Response response = protocol.createResponse(request.getUrl(), body);
        response.getHeader().addExtendData(Key.SERIALIZE, caller.getSerialize().name());
        response.setId(request.getId());
        response.setCode(code);
        return response;
    }

}
