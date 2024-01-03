package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.*;

public class ResponseFuture extends CompletableFuture<Object> {

    public static final Map<String, ResponseFuture> futures = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ResponseFuture.class);

    private String id;

    private URL url;

    private CallData callData;

    public ResponseFuture(URL url, CallData data, String id) {
        this.url = url;
        this.callData = data;
        this.id = id;
        addFuture(getId(), this);
        completeOnTimeout(null, getTimeout(), TimeUnit.MILLISECONDS);
        whenComplete((resp, ex) -> {
            removeFuture(getId());
        });
        boolean oneway = url.getBooleanParameter(Key.IS_ONEWAY);
        if(oneway){
            complete(null);
        }
    }

    public static void addFuture(String id, ResponseFuture future) {
        futures.put(id, future);
    }

    public static void removeFuture(String id) {
        futures.remove(id);
    }

    public static ResponseFuture getFuture(String id) {
        return futures.get(id);
    }

    /**
     * Returns a null if no value is returned
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public Object get() throws InterruptedException, ExecutionException {
        try {
            return super.get(getTimeout(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            logger.error("RPC call Timeout: " + getTimeout(), e);
            throw new RpcException(e);
        }
    }

    public String getId() {
        return id;
    }

    public URL getUrl() {
        return url;
    }

    public CallData getCallData() {
        return callData;
    }

    public int getTimeout() {
        return Integer.parseInt(url.getParameter(Key.TIMEOUT));
    }

    public Type getReturnType() {
        return callData.getReturnType();
    }

}
