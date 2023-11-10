package io.github.astro.mantis.monitor.response;

import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.ProviderCaller;
import io.github.astro.mantis.configuration.RemoteService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author WenBo Zhou
 * @Date 2023/10/13 14:55
 */
@Getter
@Setter
public class RemoteServiceResponse {

    private String applicationName;

    private String remoteServiceName;

    private String className;

    private List<ProviderInvokerResponse> providerInvokers = new ArrayList<>();

    public RemoteServiceResponse() {

    }

    public RemoteServiceResponse(RemoteService<?> remoteService) {
        remoteServiceName = remoteService.getRemoteServiceName();
        applicationName = remoteService.getApplicationName();
        className = remoteService.getTarget().getClass().getName();
        for (Caller caller : remoteService.getCallers()) {
            ProviderCaller providerCaller = (ProviderCaller) caller;
            providerInvokers.add(new ProviderInvokerResponse(providerCaller));
        }
    }

}
