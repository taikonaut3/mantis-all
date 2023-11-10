package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.NetUtils;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.Map;

@Getter
public class RemoteUrl extends URL implements Source {

    private String applicationName;

    private String remoteServiceName;

    private String callName;

    private String description;

    public RemoteUrl(String protocol, String address) {
        super(protocol, address);
    }

    public RemoteUrl(String protocol, String address, String applicationName, String remoteServiceName, Map<String, String> params) {
        this(protocol, NetUtils.toInetSocketAddress(address), applicationName, remoteServiceName, params);
    }

    public RemoteUrl(String protocol, String host, int port, String applicationName, String remoteServiceName, Map<String, String> params) {
        this(protocol, new InetSocketAddress(host, port), applicationName, remoteServiceName, params);
    }

    public RemoteUrl(String protocol, InetSocketAddress address, String applicationName, String remoteServiceName, Map<String, String> params) {
        super(protocol, address, params);
        this.applicationName = applicationName;
        this.remoteServiceName = remoteServiceName;
        super.addPath(applicationName);
        super.addPath(remoteServiceName);
    }

    public void setDescription(String description) {
        this.description = description;
        super.addParameter(Key.DESCRIPTION, description);
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        removePath(0);
        super.addPath(applicationName);
    }

    public void setCallName(String callName) {
        this.callName = callName;
        removePath(2);
        super.addPath(callName);
    }

    @Override
    public void addPath(String path) {
        // Not allow
    }

    @Override
    public void addPath(int index, String path) {
        // Not allow
    }

    @Override
    public String getRemoteServiceName() {
        return null;
    }

    public void setRemoteServiceName(String remoteServiceName) {
        this.remoteServiceName = remoteServiceName;
        removePath(1);
        super.addPath(remoteServiceName);
    }

}
