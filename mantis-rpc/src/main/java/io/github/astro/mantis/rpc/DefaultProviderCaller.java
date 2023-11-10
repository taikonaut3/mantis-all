package io.github.astro.mantis.rpc;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.*;
import io.github.astro.mantis.configuration.annotation.Callable;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.configuration.config.RegistryConfig;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.registry.Registry;
import io.github.astro.mantis.registry.RegistryFactory;
import io.github.astro.mantis.transport.Transporter;
import io.github.astro.mantis.transport.server.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DefaultProviderCaller extends AbstractCaller implements ProviderCaller {

    private static final Class<Callable> CALLABLE_CLASS = Callable.class;

    private volatile List<RemoteUrl> urls;

    public DefaultProviderCaller(Method method, RemoteService<?> remoteService) {
        super(method, remoteService);
    }

    private static boolean checkInvoke(Method method) {
        return method.isAnnotationPresent(CALLABLE_CLASS);
    }

    @Override
    protected void initBefore() {
        AssertUtils.assertCondition(method != null && getRemoteService() != null);
        AssertUtils.assertCondition(checkInvoke(method), "Only support @Procedure modify Method");
    }

    @Override
    protected void doInit() {
        Callable call = method.getAnnotation(CALLABLE_CLASS);
        applicationName = getRemoteService().getApplicationName();
        remoteServiceName = getRemoteService().getRemoteServiceName();
        callName = StringUtils.isBlank(call.value()) ? GenerateUtil.generateKey(getMethod()) : call.value();
        parseOption(call.option(), getRemoteService().getMantisApplication());
    }

    @Override
    protected void initAfter() {
        // 创建remoteServiceUrl
        urls = createRemoteUrl();
    }

    @Override
    public void start() {
        // 如果配置了RegistryConfig，那么注册所有的 remoteServiceURL 至注册中心
        for (RegistryConfig registryConfig : getRegistryConfigs()) {
            URL url = registryConfig.toUrl();
            RegistryFactory registryFactory = ExtensionLoader.loadService(RegistryFactory.class, url.getProtocol());
            Registry registry = registryFactory.getRegistry(url);
            for (RemoteUrl RemoteUrl : getRemoteUrls()) {
                registry.register(RemoteUrl);
            }
        }
        // 开启协议端口
        for (RemoteUrl remoteUrl : getRemoteUrls()) {
            Protocol protocol = ExtensionLoader.loadService(Protocol.class, remoteUrl.getProtocol());
            Transporter transporter = ExtensionLoader.loadService(Transporter.class, remoteUrl.getParameter(Key.TRANSPORT));
            Server server = transporter.bind(remoteUrl, protocol.getServerCodec(remoteUrl));
        }
    }

    protected List<RemoteUrl> createRemoteUrl() {
        ArrayList<RemoteUrl> urls = new ArrayList<>();
        for (ProtocolConfig protocolConfig : getProtocolConfigs()) {
            String address = NetUtils.getAddress(NetUtils.getLocalHost(), protocolConfig.getPort());
            RemoteUrl remoteUrl = new RemoteUrl(protocolConfig.getType(), address);
            remoteUrl.setApplicationName(getApplicationName());
            remoteUrl.setRemoteServiceName(getRemoteServiceName());
            remoteUrl.setCallName(callName);
            remoteUrl.addParameter(Key.CLASS, getRemoteService().getTarget().getClass().getName());
            remoteUrl.addParameters(parameterization());
            remoteUrl.addParameters(protocolConfig.parameterization());
            urls.add(remoteUrl);
        }
        return urls;
    }

    @Override
    public Object call(CallData data) {
        method.setAccessible(true);
        try {
            return method.invoke(getRemoteService().getTarget(), data.getArgs());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
    }

    public RemoteUrl[] getRemoteUrls() {
        return urls.toArray(new RemoteUrl[0]);
    }

    @Override
    public RemoteService<?> getRemoteService() {
        return (RemoteService<?>) getCallerContainer();
    }

}
