package io.github.astro.mantis.configuration.spi;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceInterface {

    String value() default "";

}
