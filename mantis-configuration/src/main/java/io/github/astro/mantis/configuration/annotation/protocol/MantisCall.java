package io.github.astro.mantis.configuration.annotation.protocol;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CallProtocol("MANTIS")
public @interface MantisCall {

    String service() default "";

    String callMethod() default "";
}
