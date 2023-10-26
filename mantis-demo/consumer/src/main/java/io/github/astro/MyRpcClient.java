package io.github.astro;

import io.github.astro.consumer.People;
import io.github.astro.mantis.ParentObject;
import io.github.astro.mantis.configuration.annotation.Invoke;
import io.github.astro.mantis.configuration.annotation.RemoteCall;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author WenBo Zhou
 * @Date 2023/4/25 14:48
 */
@RemoteCall(value = "rpc-provider")
@Qualifier("xixixixi")
public interface MyRpcClient {

    @Invoke(value = "textExportConfig", methodKey = "hello", retires = 4)
    String hello(String word);

    @Invoke(value = "textExportConfig", methodKey = "hello", async = true)
    CompletableFuture<String> helloAsync(String word);

    @Invoke("textExportConfig")
    People getPeople(List<People> people);

    @Invoke("textExportConfig")
    People getPeople(People people);

    @Invoke(value = "textExportConfig", methodKey = "getParents", timeout = 6000, async = true)
    CompletableFuture<List<ParentObject>> getParents(List<ParentObject> parentObjects, List<ParentObject> parentObjects2);
}
