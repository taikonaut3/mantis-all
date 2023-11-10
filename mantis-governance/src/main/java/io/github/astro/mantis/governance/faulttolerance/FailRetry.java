package io.github.astro.mantis.governance.faulttolerance;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.DirectRemoteCall;
import io.github.astro.mantis.configuration.Result;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.astro.mantis.common.constant.KeyValues.FaultTolerance.FAIL_RETRY;

@ServiceProvider(FAIL_RETRY)
public class FailRetry extends AbstractFaultTolerance {

    private static final Logger logger = LoggerFactory.getLogger(FailRetry.class);

    @Override
    public Result doOperation(URL url, CallData callData, DirectRemoteCall directRemoteCall) {
        int retries = Integer.parseInt(url.getParameter(Key.RETRIES, String.valueOf(Constant.DEFAULT_RETIRES)));
        for (int start = 0; start < retries; start++) {
            try {
                return directRemoteCall.call(url, callData);
            } catch (Exception e) {
                logger.error("调用服务出现异常: {},开始重试第 {} 次", e.getMessage(), start + 1);
            }
        }
        throw new RpcException("调用服务出现异常,重试次数: " + retries);
    }

}
