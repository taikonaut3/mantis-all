package io.github.astro.mantis.spring.boot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MantisPostProcessorRegister.class})
public @interface EnableMantis {

    String[] scanBasePackages() default {};

    String[] callsScan() default {};

    String[] exportsScan() default {};
}
