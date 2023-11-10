package io.github.astro.mantis.configuration.config;

import io.github.astro.mantis.common.constant.ConfigScope;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.Parameterization;
import io.github.astro.mantis.configuration.URL;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Getter
@Setter
public abstract class UrlTypeConfig implements Parameterization {

    protected String name;

    protected String host;

    protected int port;

    protected ConfigScope scope = ConfigScope.ALONE;

    public abstract URL toUrl();

    public String getAddress() {
        return NetUtils.getAddress(host, port);
    }

    public void setAddress(String address) {
        InetSocketAddress inetSocketAddress = NetUtils.toInetSocketAddress(address);
        this.host = inetSocketAddress.getHostString();
        this.port = inetSocketAddress.getPort();
    }

    @Override
    public String toString() {
        return toUrl().toString();
    }

}
