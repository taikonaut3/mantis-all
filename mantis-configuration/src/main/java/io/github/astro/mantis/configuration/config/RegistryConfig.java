package io.github.astro.mantis.configuration.config;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.annotation.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class RegistryConfig extends UrlTypeConfig {

    private String type;

    @Parameter(Key.USERNAME)
    private String username;

    @Parameter(Key.PASSWORD)
    private String password;

    @Parameter(Key.CONNECT_TIMEOUT)
    private int connectTimeout = Constant.DEFAULT_CONNECT_TIMEOUT;

    @Parameter(Key.SESSION_TIMEOUT)
    private int sessionTimeout = Constant.DEFAULT_SESSION_TIMEOUT;

    @Parameter(Key.RETRIES)
    private int retries = Constant.DEFAULT_RETIRES;

    @Parameter(Key.RETRY_INTERVAL)
    private int retryInterval = Constant.DEFAULT_INTERVAL;

    @Parameter(Key.ENABLED)
    private boolean enabled = false;

    @Override
    public URL toUrl() {
        return new URL(type, host, port, parameterization());
    }

}
