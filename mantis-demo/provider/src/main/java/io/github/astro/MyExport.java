package io.github.astro;

import io.github.astro.mantis.ParentObject;
import io.github.astro.mantis.common.constant.KeyValues;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.annotation.Callable;
import io.github.astro.mantis.configuration.annotation.Option;
import io.github.astro.mantis.configuration.annotation.RemoteService;
import jakarta.annotation.Resource;

import java.util.List;

@RemoteService("myExport")
public class MyExport {

    @Resource
    private MantisApplication mantisApplication;

    private int i = 0;

    @Callable
    public String hello(String word) {
        System.out.println("----------------------->" + i);
        if (i++ < 3) {
            int i = 1 / 0;
        }
        return "hello" + word;
    }

    @Callable
    public String helloAsync(String word) {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return "hello async" + word;
    }

    @Callable(option = @Option(serialize = KeyValues.Serialize.KRYO))
    List<ParentObject> getParents(List<ParentObject> parentObjects, List<ParentObject> parentObjects2) {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return parentObjects;
    }

}
