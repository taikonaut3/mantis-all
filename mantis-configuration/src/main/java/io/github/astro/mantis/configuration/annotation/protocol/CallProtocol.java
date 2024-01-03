package io.github.astro.mantis.configuration.annotation.protocol;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CallProtocol {
    String value() default "";
}
