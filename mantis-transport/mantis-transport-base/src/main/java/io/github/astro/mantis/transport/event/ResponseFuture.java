package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.invoke.Invocation;
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

    private Invocation invocation;

    public ResponseFuture(URL url, Invocation invocation, String id) {
        this.url = url;
        this.invocation = invocation;
        this.id = id;
        addFuture(getId(), this);
        completeOnTimeout(null, getTimeout(), TimeUnit.MILLISECONDS)
                .thenAccept(response -> {
                    if (response == null) {
                        logger.error("RPC request timed out: " + getTimeout() + "ms,url:" + url);
                    } else {
                        logger.debug("RPC response received requestId-{}", id);
                    }
                });
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

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        try {
            return super.get(getTimeout(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            logger.error("rpc 调用超时，超时时间：" + getTimeout());
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }

    public int getTimeout() {
        return Integer.parseInt(url.getParameter(Key.TIMEOUT));
    }

    public Type getReturnType() {
        return invocation.getReturnType();
    }

    public void delete() {
        removeFuture(getId());
    }
}
