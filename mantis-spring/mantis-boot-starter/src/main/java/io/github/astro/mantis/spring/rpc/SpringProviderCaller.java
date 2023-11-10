package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.RemoteService;
import io.github.astro.mantis.configuration.RemoteUrl;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.rpc.DefaultProviderCaller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpringProviderCaller extends DefaultProviderCaller {

    public SpringProviderCaller(Method method, RemoteService<?> remoteService) {
        super(method, remoteService);
    }

    protected List<RemoteUrl> createRemoteServiceUrls() {
        ArrayList<RemoteUrl> urls = new ArrayList<>();
        String ipAddress = NetUtils.getLocalHost();
        for (ProtocolConfig protocolConfig : getProtocolConfigs()) {
            String address = NetUtils.getAddress(ipAddress, protocolConfig.getPort());
            RemoteUrl RemoteUrl = new RemoteUrl(protocolConfig.getType(), address);
            RemoteUrl.setApplicationName(getApplicationName());
            RemoteUrl.setRemoteServiceName(getRemoteServiceName());
            RemoteUrl.setCallName(callName);
            RemoteUrl.addParameter(Key.CLASS, getRemoteService().getTarget().getClass().getName());
            RemoteUrl.addParameters(parameterization());
            urls.add(RemoteUrl);
        }
        return urls;
    }

}
