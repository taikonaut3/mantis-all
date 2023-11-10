package io.github.astro.mantis.monitor.response;

import io.github.astro.mantis.configuration.ProviderCaller;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ProviderInvokerResponse {

    private String method;

    private List<String> urls;

    public ProviderInvokerResponse() {

    }

    public ProviderInvokerResponse(ProviderCaller caller) {
        method = GenerateUtil.generateKey(caller.getMethod());
        urls = Arrays.stream(caller.getRemoteUrls()).sorted().map(URL::toString).toList();
    }

}
