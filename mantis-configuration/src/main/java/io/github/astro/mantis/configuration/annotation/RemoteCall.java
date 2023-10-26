package io.github.astro.mantis.configuration.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteCall {

    /**
     * application_name
     */
    String value();
}
