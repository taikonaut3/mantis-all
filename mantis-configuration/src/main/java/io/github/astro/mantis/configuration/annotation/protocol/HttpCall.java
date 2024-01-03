package io.github.astro.mantis.configuration.annotation.protocol;

import java.lang.annotation.*;

/**
 * Annotation used to define an HTTP call.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CallProtocol("HTTP")
public @interface HttpCall {

    /**
     * The path of the HTTP request.
     */
    String path() default "";

    /**
     * The method of the HTTP request.
     */
    String method() default "";

    /**
     * The headers of the HTTP request.
     */
    String[] headers() default {};

    /**
     * The path variables of the HTTP request.
     */
    String[] pathVariables() default {};

    /**
     * The query parameters of the HTTP request.
     */
    String[] queryParams() default {};

    /**
     * The encoding of the HTTP request.
     */
    String encoding() default "";
}

