package io.github.astro;

import io.github.astro.mantis.ParentObject;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.annotation.Export;
import io.github.astro.mantis.configuration.annotation.Procedure;
import jakarta.annotation.Resource;

import java.util.List;

@Export(value = "textExportConfig")
public class MyExport {

    @Resource
    private MantisBootStrap mantisBootStrap;

    private int i = 0;

    @Procedure("hello")
    public String hello(String word) {
//        if(i++<3){
//            int i = 1/0;
//        }
        return "hello" + word;
    }

    @Procedure
    public String helloAsync(String word) {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return "hello async" + word;
    }

    @Procedure(value = "getParents", processors = {"test"})
    List<ParentObject> getParents(List<ParentObject> parentObjects, List<ParentObject> parentObjects2) {
        return parentObjects;
    }

}
