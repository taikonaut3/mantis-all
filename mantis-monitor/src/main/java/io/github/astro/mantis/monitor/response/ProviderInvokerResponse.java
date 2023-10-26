package io.github.astro.mantis.monitor.response;

import io.github.astro.mantis.configuration.ProviderInvoker;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProviderInvokerResponse {

    private String method;

    private List<String> urls;

    public ProviderInvokerResponse() {

    }

    public ProviderInvokerResponse(ProviderInvoker invoker) {
        method = GenerateUtil.generateKey(invoker.getMethod());
        urls = invoker.getExporterUrls().stream().map(URL::toString).toList();
    }
}
