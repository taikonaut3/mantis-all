package io.github.astro;

import io.github.astro.mantis.ChildObject;
import io.github.astro.mantis.GrandchildObject;
import io.github.astro.mantis.ParentObject;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.proxy.jdk.JDKProxyFactory;
import io.github.astro.mantis.rpc.DefaultRemoteCaller;
import io.github.astro.mantis.spring.boot.EnableMantis;
import io.guthub.astro.mantis.proxy.ProxyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@EnableMantis(scanBasePackages = {"io.github.astro"})
public class Consumer {

    public static void main(String[] args) throws Exception {
        MyRpcClient client = getClientBySpring(args);
        //nestObjInvoke(client);
        System.in.read();

    }

    public static MyRpcClient getClientBySpring(String[] args) {
        ApplicationContext context = SpringApplication.run(Consumer.class, args);
        return context.getBean(MyRpcClient.class);
    }

    public static MyRpcClient getClientByNormal() {
        MantisApplication mantisApplication = new MantisApplication();
        DefaultRemoteCaller<MyRpcClient> remoteCaller = new DefaultRemoteCaller<>(mantisApplication, MyRpcClient.class);
        return remoteCaller.get();
    }

    public static void asyncInvoke(MyRpcClient client) throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<String> future = client.helloAsync("异步....1111==============");
        CompletableFuture<String> future1 = client.helloAsync("异步....2222==============");
        CompletableFuture<String> future2 = client.helloAsync("异步....3333=============");
        System.out.println(future.get());
        System.out.println("调用时间为：" + (System.currentTimeMillis() - start));
        System.out.println(future1.get());
        System.out.println("调用时间为：" + (System.currentTimeMillis() - start));
        System.out.println(future2.get());
        System.out.println("调用时间为：" + (System.currentTimeMillis() - start));
    }

    public static void nestObjInvoke(MyRpcClient client) throws Exception {
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
        List<ParentObject> parents = client.getParents(parentObjects, parentObjects).get();
        System.out.println("调用时间为：" + (System.currentTimeMillis() - start));
        System.out.println(parents);
    }

    public static void testProxy() {
        List<ProxyFactory> proxyFactories = ExtensionLoader.loadServices(ProxyFactory.class);
        for (ProxyFactory proxyFactory : proxyFactories) {
            if(proxyFactory instanceof JDKProxyFactory){
                continue;
            }
            long avgTime = 0;

            for (int i = 0; i < 100; i++) {
                long start = System.currentTimeMillis();
                MyService proxyObj = proxyFactory.createProxy(new MyService(), (proxy, method, args, superInvoker) -> null);
                long end = System.currentTimeMillis();
                long time = end - start;
                System.out.println(proxyFactory+"单次时间："+time);
                avgTime = avgTime + time;
            }
            System.out.println(proxyFactory + "平均花费时间：" + (avgTime));
        }
    }

}
