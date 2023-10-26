package io.github.astro.mantis.rpc.configuration;

import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.ConsumerInvoker;
import io.github.astro.mantis.configuration.InvokerProcessor;
import io.github.astro.mantis.configuration.RegistryConfig;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.invoke.Result;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.governance.directory.Directory;
import io.github.astro.mantis.governance.faulttolerance.FaultTolerance;
import io.github.astro.mantis.governance.loadbalance.LoadBalance;
import io.github.astro.mantis.governance.router.Router;

import java.util.Arrays;
import java.util.List;

public class MantisInvoker {

    private final ConsumerInvoker invoker;

    private Directory directory;

    private Router router;

    private LoadBalance loadBalance;

    private FaultTolerance faultTolerance;

    public MantisInvoker(ConsumerInvoker invoker) {
        this.invoker = invoker;
        initInvoke();

    }

    public void initInvoke() {
        this.faultTolerance = ServiceProviderLoader.loadService(FaultTolerance.class, invoker.getFaultTolerance());
        this.loadBalance = ServiceProviderLoader.loadService(LoadBalance.class, invoker.getLoadBalance());
        // todo getDefault
        this.router = ServiceProviderLoader.loadService(Router.class, invoker.getRouter());
        this.directory = ServiceProviderLoader.loadService(Directory.class, invoker.getDirectory());
    }

    public Result invoke(Invocation invocation) {
        for (InvokerProcessor processor : invoker.getProcessors()) {
            processor.processBeforeSelect(invoker, invocation);
        }
        // todo 优化调用注册中心的逻辑
        URL url = selectURL(invocation);
        url.addParameters(((DefaultConsumerInvoker) invoker).getParams());
        for (InvokerProcessor processor : invoker.getProcessors()) {
            processor.processAfterSelect(invoker, invocation, url);
        }
        return faultTolerance.operation(url, invocation);
    }

    public URL selectURL(Invocation invocation) {
        if (!StringUtils.isBlank(invoker.getDirectUrl())) {
            URL url = URL.valueOf(invoker.getDirectUrl());
            url.addPath(invocation.getApplicationName());
            url.addPath(invocation.getExportName());
            url.addPath(invocation.getMethodKey());
            return url;
        }
        List<URL> urls = discoveryUrls(invocation);
        urls = router.route(urls, invocation);
        return loadBalance.select(urls, invocation);
    }

    private List<URL> discoveryUrls(Invocation invocation) {
        URL[] urls = Arrays.stream(invoker.getRegistryConfigs()).map(RegistryConfig::toUrl).toArray(URL[]::new);
        List<URL> result = directory.list(invocation, urls);
        if (result.isEmpty()) {
            throw new SourceException("Not Find available URL!" + GenerateUtil.generateKey(invocation));
        }
        return result;
    }
}
