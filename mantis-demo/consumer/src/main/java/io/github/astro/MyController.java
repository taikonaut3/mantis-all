package io.github.astro;

import io.github.astro.mantis.ChildObject;
import io.github.astro.mantis.GrandchildObject;
import io.github.astro.mantis.ParentObject;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author WenBo Zhou
 * @Date 2023/10/8 16:52
 */
@RestController
public class MyController {

    @Resource
    MyRpcClient client;

    @Resource
    ApplicationContext context;

    @RequestMapping("/helloSync/{word}")
    public String helloSync(@PathVariable("word") String word) {
        StringBuilder builder = new StringBuilder();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            builder.append(client.hello(word + +i + i + i)).append("\n");
        }
        long end = System.currentTimeMillis();
        return builder + "花费的时间：" + (end - start);
    }

    @RequestMapping("/helloAsync/{word}")
    public String helloAsync(@PathVariable("word") String word) throws ExecutionException, InterruptedException {
        StringBuilder builder = new StringBuilder();
        long start = System.currentTimeMillis();
        ArrayList<CompletableFuture<String>> completableFutures = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            CompletableFuture<String> future = client.helloAsync(word + i + i + i);
            completableFutures.add(future);
        }
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new));
        long end = System.currentTimeMillis();
        for (CompletableFuture<String> completableFuture : completableFutures) {
            builder.append(completableFuture.get()).append("\n");
        }
        return builder + "花费的时间：" + (end - start);
    }

    @RequestMapping("getNestObj")
    public List<ParentObject> getParentObj() {
        // 创建嵌套对象
        ArrayList<ParentObject> parentObjects = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            ParentObject parent = new ParentObject();
            parent.setSomeValue("Parent Value" + i + i + i);

            ChildObject child = new ChildObject();
            child.setSomeValue("Child Value" + i + i + i);

            GrandchildObject grandchild = new GrandchildObject();
            grandchild.setSomeValue("Grandchild Value" + i + i + i);

            // 将子对象和孙子对象添加到父对象中
            child.setGrandchild(grandchild);
            parent.setChild(child);
            parentObjects.add(parent);
        }
        long start = System.currentTimeMillis();
        try {
            return client.getParents(parentObjects, parentObjects).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
            long end = System.currentTimeMillis();
            System.out.println("花费时间："+(end-start));
        }
    }

    @RequestMapping("oneWay")
    public String oneWay() {
        client.testOneWay("1111111");
        return "success";
    }

}
