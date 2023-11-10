package io.github.astro.mantis.configuration.annotation;

import io.github.astro.mantis.common.constant.Constant;

import java.lang.annotation.*;

import static io.github.astro.mantis.common.constant.KeyValues.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Call {

    /**
     * RemoteService Name
     */
    String value() default "";

    /**
     * Call Name
     */
    String callName() default "";

    /**
     * 支持异步
     */
    boolean async() default false;

    /**
     * 超时时间：ms
     */
    int timeout() default Constant.DEFAULT_TIMEOUT;

    /**
     * 重试次数
     */
    int retires() default Constant.DEFAULT_RETIRES;

    /**
     * 请求的协议
     */
    String protocol() default Protocol.MANTIS;

    /**
     * 获取服务
     */
    String directory() default Directory.DEFAULT;

    /**
     * 路由
     */
    String router() default Router.WEIGHT;

    /**
     * 负载均衡方式
     */
    String loadBalance() default LoadBalance.RANDOM;

    /**
     * 容错方式
     */
    String faultTolerance() default FaultTolerance.FAIL_RETRY;

    /**
     * 直连调用
     */
    String url() default "";

    Option option() default @Option;

}
