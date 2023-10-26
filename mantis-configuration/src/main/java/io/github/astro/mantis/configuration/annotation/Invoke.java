package io.github.astro.mantis.configuration.annotation;

import io.github.astro.mantis.common.constant.Constant;

import java.lang.annotation.*;

import static io.github.astro.mantis.common.constant.ServiceType.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Invoke {

    /**
     * exportName
     */
    String value();

    String methodKey() default "";

    /**
     * 支持异步
     */
    boolean async() default false;

    String version() default "1.0.0";

    String group() default "default";

    String url() default "";

    /**
     * 请求的协议
     */
    String protocol() default Protocol.MANTIS;

    /**
     * 代理方式
     */
    String proxy() default ProxyFactory.JDK;

    /**
     * 序列化方式
     */
    String serialize() default Serializer.KRYO;

    /**
     * 负载均衡方式
     */
    String loadBalance() default LoadBalance.RANDOM;

    /**
     * 路由
     */
    String router() default Router.WEIGHT;

    /**
     * 获取服务
     */
    String directory() default Directory.DEFAULT;

    /**
     * 容错方式
     */
    String faultTolerance() default FaultTolerance.FAIL_RETRY;

    /**
     * 网络传输框架
     */
    String transport() default Transport.NETTY;

    /**
     * 超时时间：ms
     */
    int timeout() default Constant.DEFAULT_TIMEOUT;

    /**
     * 重试次数
     */
    int retires() default Constant.DEFAULT_RETIRES;

    /**
     * 支持多注册中心获取信息
     */
    String[] registries() default {};

    /**
     * 配置过滤器
     */
    String[] processors() default {};

}
