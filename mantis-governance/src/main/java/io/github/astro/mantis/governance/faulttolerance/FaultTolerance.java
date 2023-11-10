package io.github.astro.mantis.governance.faulttolerance;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.DirectRemoteCall;
import io.github.astro.mantis.configuration.Result;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;

import static io.github.astro.mantis.common.constant.KeyValues.FaultTolerance.FAIL_RETRY;

/**
 * 容错接口
 */
@ServiceInterface(FAIL_RETRY)
public interface FaultTolerance {

    Result operation(URL url, CallData callData, DirectRemoteCall directRemoteCall);

}
