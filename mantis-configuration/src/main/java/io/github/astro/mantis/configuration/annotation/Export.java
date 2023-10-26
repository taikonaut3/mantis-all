package io.github.astro.mantis.configuration.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Export {

    /**
     * exportName
     */
    String value();

    String applicationName() default "";
}
