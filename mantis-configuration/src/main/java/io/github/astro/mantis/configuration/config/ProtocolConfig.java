package io.github.astro.mantis.configuration.config;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.annotation.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class ProtocolConfig extends UrlTypeConfig {

    private String type;

    @Parameter(Key.SSL)
    private boolean ssl = true;

    @Parameter(Key.SERVER_MAX_RECEIVE_SIZE)
    private int serverMaxMessageSize = Constant.DEFAULT_MAX_MESSAGE_SIZE;

    @Parameter(Key.CLIENT_MAX_RECEIVE_SIZE)
    private int clientMaxMessageSize = Constant.DEFAULT_MAX_MESSAGE_SIZE;

    @Parameter(Key.MAX_HEADER_SIZE)
    private int maxHeaderSize = Constant.DEFAULT_MAX_HEADER_SIZE;

    @Parameter(Key.CONNECT_TIMEOUT)
    private int connectTimeout = Constant.DEFAULT_CONNECT_TIMEOUT;

    @Parameter(Key.TIMEOUT)
    private int exchangeTimeout = Constant.DEFAULT_TIMEOUT;

    @Parameter(Key.HEARTBEAT_INTERVAL)
    private int heartbeatInterval = Constant.DEFAULT_HEARTBEAT_INTERVAL;

    @Parameter(Key.SPARE_CLOSE_TIMES)
    private int spareCloseTimes = Constant.DEFAULT_SPARE_CLOSE_TIMES;

    @Parameter(Key.HEARTBEAT_LOG_ENABLE)
    private boolean heartbeatLogEnable = false;

    @Parameter(Key.SO_BACKLOG)
    private int soBacklog = Constant.DEFAULT_SO_BACKLOG;

    @Parameter(Key.COMPRESSION)
    private boolean compression = true;

    @Parameter(Key.KEEPALIVE)
    private boolean keepAlive = true;

    @Override
    public URL toUrl() {
        return new URL(type, NetUtils.getLocalHost(), port, parameterization());
    }

}
