package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.configuration.MantisBootStrap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 启动 mantis
 */
public class SpringBootMantisStarter implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MantisBootStrap mantisBootStrap = event.getApplicationContext().getBean(MantisBootStrap.class);
        mantisBootStrap.start();
    }
}
