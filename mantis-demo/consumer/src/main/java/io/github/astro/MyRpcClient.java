package io.github.astro;

import io.github.astro.consumer.People;
import io.github.astro.mantis.ParentObject;
import io.github.astro.mantis.common.constant.KeyValues;
import io.github.astro.mantis.configuration.annotation.Call;
import io.github.astro.mantis.configuration.annotation.Option;
import io.github.astro.mantis.configuration.annotation.RemoteCaller;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author WenBo Zhou
 * @Date 2023/4/25 14:48
 */
@RemoteCaller("rpc-provider")
@Qualifier("xixixixi")
public interface MyRpcClient {

    @Call("myExport")
    String hello(String word);

    @Call(value = "myExport", async = true)
    CompletableFuture<String> helloAsync(String word);

    @Call("myExport")
    People getPeople(List<People> people);

    @Call("myExport")
    People getPeople(People people);

    @Call("myExport")
    void testOneWay(String word);

    @Call(value = "myExport", async = true, option = @Option(serialize = KeyValues.Serialize.KRYO))
    CompletableFuture<List<ParentObject>> getParents(List<ParentObject> parentObjects, List<ParentObject> parentObjects2);

}
