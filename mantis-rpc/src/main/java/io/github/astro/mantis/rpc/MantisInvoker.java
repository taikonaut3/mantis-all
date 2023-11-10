package io.github.astro.mantis.rpc;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.common.util.DateUtils;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.ConsumerCaller;
import io.github.astro.mantis.configuration.Result;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.config.RegistryConfig;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.governance.directory.Directory;
import io.github.astro.mantis.governance.faulttolerance.FaultTolerance;
import io.github.astro.mantis.governance.loadbalance.LoadBalance;
import io.github.astro.mantis.governance.router.Router;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.ResponseFuture;
import io.github.astro.mantis.transport.ResponseResult;
import io.github.astro.mantis.transport.Transporter;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;

import java.time.LocalDateTime;
import java.util.List;

public class MantisInvoker {

    private final ConsumerCaller caller;

    private Directory directory;

    private Router router;

    private LoadBalance loadBalance;

    private FaultTolerance faultTolerance;

    private Protocol protocol;

    private Transporter transporter;

    public MantisInvoker(ConsumerCaller caller) {
        this.caller = caller;
        initCallComponent();

    }

    public void initCallComponent() {
        this.faultTolerance = ExtensionLoader.loadService(FaultTolerance.class, caller.getFaultTolerance());
        this.loadBalance = ExtensionLoader.loadService(LoadBalance.class, caller.getLoadBalance());
        // todo getDefault
        this.router = ExtensionLoader.loadService(Router.class, caller.getRouter());
        this.directory = ExtensionLoader.loadService(Directory.class, caller.getDirectory());
        this.protocol = ExtensionLoader.loadService(Protocol.class, caller.getProtocol().name());
        this.transporter = ExtensionLoader.loadService(Transporter.class, caller.getTransport());
    }

    public Result invoke(CallData callData) throws RpcException {
        // todo 优化调用注册中心的逻辑
        URL url = selectURL(callData);
        url.addParameters(caller.parameterization());
        return faultTolerance.operation(url, callData, this::call);
    }

    public URL selectURL(CallData callData) {
        if (!StringUtils.isBlank(caller.getDirectUrl())) {
            URL url = URL.valueOf(caller.getDirectUrl());
            url.addPath(caller.getApplicationName());
            url.addPath(caller.getRemoteServiceName());
            url.addPath(caller.getCallName());
            return url;
        }
        List<URL> urls = discoveryUrls(callData);
        urls = router.route(urls, callData);
        return loadBalance.select(urls, callData);
    }

    private List<URL> discoveryUrls(CallData callData) {
        URL[] urls = caller.getRegistryConfigs().stream().map(RegistryConfig::toUrl).toArray(URL[]::new);
        List<URL> result = directory.list(callData, urls);
        if (result.isEmpty()) {
            throw new SourceException("Not Find available URL!" + GenerateUtil.generateKey(callData));
        }
        return result;
    }

    public Result call(URL url, CallData callData) {
        Codec codec = protocol.getClientCodec(url);
        Client client = transporter.connect(url, codec);
        boolean isAsync = url.getBooleanParameter(Key.IS_ASYNC);
        String timestamp = DateUtils.format(LocalDateTime.now(), DateUtils.COMPACT_FORMAT);
        url.addParameter(Key.TIMESTAMP, timestamp);
        Request request = protocol.createRequest(url, callData);
        ResponseFuture future = new ResponseFuture(url, callData, request.getId());
        // request
        client.getChannel().send(request);
        ResponseResult result = new ResponseResult(future, isAsync);
        if (!isAsync) {
            result.getValue();
        }
        return result;
    }

}
