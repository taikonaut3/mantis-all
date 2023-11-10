package io.github.astro.mantis.configuration.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Callable {

    /**
     * Call Name
     */
    String value() default "";

    String description() default "";

    Option option() default @Option;

}
