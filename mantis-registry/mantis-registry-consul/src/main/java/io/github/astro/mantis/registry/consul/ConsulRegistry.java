package io.github.astro.mantis.registry.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Self;
import com.ecwid.consul.v1.kv.model.GetValue;
import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.RemoteUrl;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.registry.AbstractRegistry;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;

public class ConsulRegistry extends AbstractRegistry {

    private ConsulClient consulClient;

    private HttpClient httpClient;

    public ConsulRegistry(URL url) {
        super(url);
    }

    @Override
    protected void initClient(URL url) {
        HttpClient httpClient = createHttpClient(url);
        ConsulRawClient consulRawClient = new ConsulRawClient(url.getHost(), url.getPort(), httpClient);
        consulClient = new ConsulClient(consulRawClient);
    }

    private HttpClient createHttpClient(URL url) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(url.getIntParameter(Key.CONNECT_TIMEOUT))
                .setSocketTimeout(url.getIntParameter(Key.SESSION_TIMEOUT))
                .build();
        HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(url.getIntParameter(Key.RETRIES), true);
        // 创建 HttpClient 实例
        return HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setRetryHandler(retryHandler)
                .build();

    }

    @Override
    protected void doConnect(URL url) {

    }

    @Override
    protected void doRegister(RemoteUrl url) {
        String path = Constant.SERVICES_DIR + GenerateUtil.generateKey(url);
        // consul这个客户端不需要 "/" 开头
        String substring = path.substring(1);
        consulClient.setKVValue(substring, url.toString());
    }

    @Override
    protected URL doDiscover(CallData callData) {
        Response<GetValue> response = consulClient.getKVValue(Constant.SERVICES_DIR + GenerateUtil.generateKey(callData));
        if (response.getValue() != null) {
            String urlStr = response.getValue().getDecodedValue();
            return URL.valueOf(urlStr);
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        Response<Self> agentSelf = consulClient.getAgentSelf();
        return agentSelf.getValue() != null;
    }

    @Override
    public void subscribe(URL url) {
    }

    @Override
    public void unSubscribe(URL url) {

    }

    @Override
    public void destroy() {

    }

}
